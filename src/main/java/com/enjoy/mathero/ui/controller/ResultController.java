package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.*;
import com.enjoy.mathero.ui.model.request.DuoResultRequestModel;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResultController {

    @Autowired
    UserService userService;

    @Autowired
    ResultService resultService;

    @Autowired
    ClassService classService;

    @GetMapping(path = "/results/{resultId}")
    public SoloResultRest getResult(@PathVariable String resultId){
        return null;
    }

    @GetMapping(path = "/results/top10")
    public CustomList<SoloResultRest> getTop10(@RequestParam(required = false, name = "stageNumber") Integer stageNumber){
        CustomList<SoloResultRest> returnValue = new CustomList<>();

        List<SoloResultDto> soloResultDtos;

        if(stageNumber == null){
            soloResultDtos = resultService.getTop10();
        }
        else{
            soloResultDtos = resultService.getTop10(stageNumber);
        }
        for(SoloResultDto soloResultDto: soloResultDtos){
            SoloResultRest soloResultRest = new SoloResultRest();
            BeanUtils.copyProperties(soloResultDto, soloResultRest);
            soloResultRest.setUserId(soloResultDto.getUserDetails().getUserId());
            returnValue.add(soloResultRest);
        }
        return returnValue;
    }

    @GetMapping(path = "/results")
    public List<SoloResultRest> getAllResults(){
        return null;
    }

    @PostMapping(path = "/users/{userId}/results")
    public SoloResultRest createResult(@PathVariable String userId, @RequestBody SoloResultRequestModel resultDetails){
        SoloResultRest returnValue = new SoloResultRest();

        UserDto userDto = userService.getUserByUserId(userId);
        if(userDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        SoloResultDto soloResultDto = new SoloResultDto();
        soloResultDto.setUserDetails(userDto);
        BeanUtils.copyProperties(resultDetails, soloResultDto);

        SoloResultDto createdResult = resultService.createSoloResult(soloResultDto);
        BeanUtils.copyProperties(createdResult, returnValue);
        returnValue.setUserId(userDto.getUserId());

        return returnValue;
    }

    @PostMapping(path = "/results/duo")
    public DuoResultRest createDuoResult(@RequestParam(name = "userId1") String userId1, @RequestParam(name = "userId2") String userId2, @RequestBody DuoResultRequestModel resultDetails){
        DuoResultRest returnValue = new DuoResultRest();

        UserDto userDto1 = userService.getUserByUserId(userId1);
        UserDto userDto2 = userService.getUserByUserId(userId2);

        if(userDto1 == null || userDto2==null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        DuoResultDto duoResultDto = new DuoResultDto();
        duoResultDto.setUserDetails1(userDto1);
        duoResultDto.setUserDetails2(userDto2);
        BeanUtils.copyProperties(resultDetails, duoResultDto);

        DuoResultDto createdResult = resultService.createDuoResult(duoResultDto);
        BeanUtils.copyProperties(createdResult, returnValue);
        returnValue.setUserId1(userDto1.getUserId());
        returnValue.setUserId2(userDto2.getUserId());

        return returnValue;
    }

    @GetMapping(path = "/results/duo/top10")
    public CustomList<DuoResultRest> getTop10Duo(@RequestParam(required = false, name = "stageNumber") Integer stageNumber){
        CustomList<DuoResultRest> returnValue = new CustomList<>();

        List<DuoResultDto> duoResultDtos;

        if(stageNumber == null){
            duoResultDtos = resultService.getTop10Duo();
        }
        else{
            duoResultDtos = resultService.getTop10Duo(stageNumber);
        }
        for(DuoResultDto duoResultDto: duoResultDtos){
            DuoResultRest duoResultRest = new DuoResultRest();
            BeanUtils.copyProperties(duoResultDto, duoResultRest);
            duoResultRest.setUserId1(duoResultDto.getUserDetails1().getUserId());
            duoResultRest.setUserId2(duoResultDto.getUserDetails2().getUserId());
            returnValue.add(duoResultRest);
        }
        return returnValue;

    }

    @GetMapping(path = "/users/{userId}/summary-report")
    public StageSummaryReportRest getStageSummaryReport(@RequestParam(name = "stageNumber") int stageNumber, @PathVariable String userId){
        StageSummaryReportRest returnValue = new StageSummaryReportRest();

        UserDto userDto = userService.getUserByUserId(userId);
        if(userDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        StageSummaryReportDto stageSummaryReportDto = resultService.getStageSummaryReportByUserId(userId, stageNumber);
        BeanUtils.copyProperties(stageSummaryReportDto, returnValue);

        return returnValue;

    }

    @GetMapping(path = "/users/{userId}/summary-report-all")
    public CustomList<StageSummaryReportRest> getAllStageSummaryReport(@PathVariable String userId){
        CustomList<StageSummaryReportRest> returnValue = new CustomList<>();

        UserDto userDto = userService.getUserByUserId(userId);
        if(userDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<StageSummaryReportDto> results = resultService.getAllStagesReportsByUserId(userId);

        for(StageSummaryReportDto dto: results){
            StageSummaryReportRest rest = new StageSummaryReportRest();
            BeanUtils.copyProperties(dto, rest);
            returnValue.add(rest);
        }

        return returnValue;

    }

    @GetMapping(path = "/classes/{classId}/summary-report")
    public ClassStageSummaryRest getClassStageSummary(@RequestParam(name = "stageNumber") int stageNumber, @PathVariable String classId){
        ClassStageSummaryRest returnValue = new ClassStageSummaryRest();

        ClassDto classDto = classService.getClassByClassId(classId);
        if(classDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ClassStageSummaryDto stageSummaryReportDto = resultService.getClassStageSummaryByClassId(classId, stageNumber);
        BeanUtils.copyProperties(stageSummaryReportDto, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/classes/{classId}/summary-report-all")
    public CustomList<ClassStageSummaryRest> getAllClassStageSummary(@PathVariable String classId){
        CustomList<ClassStageSummaryRest> returnValue = new CustomList<>();

        ClassDto classDto = classService.getClassByClassId(classId);
        if(classDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<ClassStageSummaryDto> results = resultService.getAllClassStageSummaryByClassId(classId);

        for(ClassStageSummaryDto dto: results){
            ClassStageSummaryRest rest = new ClassStageSummaryRest();
            BeanUtils.copyProperties(dto, rest);
            returnValue.add(rest);
        }

        return returnValue;

    }

}
