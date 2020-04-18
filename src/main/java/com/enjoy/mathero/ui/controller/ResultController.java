package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.InvalidRequestBodyException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.*;
import com.enjoy.mathero.ui.model.request.DuoResultRequestModel;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.response.*;
import com.enjoy.mathero.ui.validator.DuoResultValidator;
import com.enjoy.mathero.ui.validator.SoloResultValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Result Controller", description = "Endpoints connected with results")
@RestController
public class ResultController {

    @Autowired
    UserService userService;

    @Autowired
    ResultService resultService;

    @Autowired
    DuoResultValidator duoResultValidator;

    @ApiOperation(value = "Return top 20 solo results")
    @GetMapping(path = "/results/top20")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<SoloResultRest> getTop20(
            @ApiParam(value = "Stage number for which top 20 results will be retrieved (top 20 from all stages if not specified)") @RequestParam(required = false, name = "stageNumber") Integer stageNumber){
        CustomList<SoloResultRest> returnValue = new CustomList<>();

        List<SoloResultDto> soloResultDtos;

        if(stageNumber == null){
            soloResultDtos = resultService.getTop20();
        }
        else{
            soloResultDtos = resultService.getTop20(stageNumber);
        }
        for(SoloResultDto soloResultDto: soloResultDtos){
            SoloResultRest soloResultRest = new SoloResultRest();
            BeanUtils.copyProperties(soloResultDto, soloResultRest);
            soloResultRest.setUserId(soloResultDto.getUserDetails().getUserId());
            soloResultRest.setUsername(soloResultDto.getUserDetails().getUsername());
            returnValue.add(soloResultRest);
        }
        return returnValue;
    }

    @ApiOperation(value = "Create duo result")
    @PostMapping(path = "/results/duo")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public DuoResultRest createDuoResult(
            @ApiParam(value = "User id of first player", required = true) @RequestParam(name = "userId1") String userId1,
            @ApiParam(value = "User id of second player", required = true) @RequestParam(name = "userId2") String userId2,
            @ApiParam(value = "Duo result details to store in the database", required = true) @RequestBody DuoResultRequestModel resultDetails,
            BindingResult result){
        duoResultValidator.validate(resultDetails, result);

        if(result.hasErrors())
            throw new InvalidRequestBodyException(result);

        DuoResultRest returnValue = new DuoResultRest();

        UserDto userDto1 = userService.getUserByUserId(userId1);
        UserDto userDto2 = userService.getUserByUserId(userId2);

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

    @ApiOperation(value = "Return top 20 duo results")
    @GetMapping(path = "/results/duo/top20")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<DuoResultRest> getTop20Duo(
            @ApiParam(value = "Stage number for which top 20 results will be retrieved (top 20 from all stages if not specified)") @RequestParam(required = false, name = "stageNumber") Integer stageNumber){
        CustomList<DuoResultRest> returnValue = new CustomList<>();

        List<DuoResultDto> duoResultDtos;

        if(stageNumber == null){
            duoResultDtos = resultService.getTop20Duo();
        }
        else{
            duoResultDtos = resultService.getTop20Duo(stageNumber);
        }
        for(DuoResultDto duoResultDto: duoResultDtos){
            DuoResultRest duoResultRest = new DuoResultRest();
            BeanUtils.copyProperties(duoResultDto, duoResultRest);
            duoResultRest.setUserId1(duoResultDto.getUserDetails1().getUserId());
            duoResultRest.setUserId2(duoResultDto.getUserDetails2().getUserId());
            duoResultRest.setUsername1(duoResultDto.getUserDetails1().getUsername());
            duoResultRest.setUsername2(duoResultDto.getUserDetails2().getUsername());
            returnValue.add(duoResultRest);
        }
        return returnValue;

    }

}
