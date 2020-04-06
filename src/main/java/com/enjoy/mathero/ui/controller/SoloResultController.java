package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.SoloResultEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.SoloResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.*;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.response.ClassStageSummaryRest;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import com.enjoy.mathero.ui.model.response.SoloResultRest;
import com.enjoy.mathero.ui.model.response.StageSummaryReportRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SoloResultController {

    @Autowired
    UserService userService;

    @Autowired
    SoloResultService resultService;

    @Autowired
    ClassService classService;

    @GetMapping(path = "/results/{resultId}")
    public SoloResultRest getResult(@PathVariable String resultId){
        return null;
    }

    @GetMapping(path = "/users/{userId}/results")
    public CustomList<SoloResultRest> getResultsByUserId(@PathVariable String userId){
        CustomList<SoloResultRest> returnValue = new CustomList<>();

        List<SoloResultDto> soloResultDtos = resultService.getSoloResultsByUserId(userId);
        for(SoloResultDto soloResultDto: soloResultDtos){
            SoloResultRest soloResultRest = new SoloResultRest();
            BeanUtils.copyProperties(soloResultDto, soloResultRest);
            soloResultRest.setUserId(soloResultDto.getUserDetails().getUserId());
            returnValue.add(soloResultRest);
        }

        return returnValue;
    }

    @GetMapping(path = "/results/top10")
    public CustomList<SoloResultRest> getTop10(){
        CustomList<SoloResultRest> returnValue = new CustomList<>();

        List<SoloResultDto> soloResultDtos = resultService.getTop10();
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

    @GetMapping(path = "/classes/{className}/summary-report")
    public ClassStageSummaryRest getClassStageSummary(@RequestParam(name = "stageNumber") int stageNumber, @PathVariable String className){
        ClassStageSummaryRest returnValue = new ClassStageSummaryRest();

        ClassDto classDto = classService.getClassByClassName(className);
        if(classDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ClassStageSummaryDto stageSummaryReportDto = resultService.getClassStageSummaryByClassName(className, stageNumber);
        BeanUtils.copyProperties(stageSummaryReportDto, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/classes/{className}/summary-report-all")
    public CustomList<ClassStageSummaryRest> getAllClassStageSummary(@PathVariable String className){
        CustomList<ClassStageSummaryRest> returnValue = new CustomList<>();

        ClassDto classDto = classService.getClassByClassName(className);
        if(classDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<ClassStageSummaryDto> results = resultService.getAllClassStageSummaryByClassName(className);

        for(ClassStageSummaryDto dto: results){
            ClassStageSummaryRest rest = new ClassStageSummaryRest();
            BeanUtils.copyProperties(dto, rest);
            returnValue.add(rest);
        }

        return returnValue;

    }

}
