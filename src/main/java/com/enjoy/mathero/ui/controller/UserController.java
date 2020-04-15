package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.InvalidRequestBodyException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.StageSummaryReportDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.MaxStageRequestModel;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.request.UserDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.*;
import com.enjoy.mathero.ui.validator.MaxStageValidator;
import com.enjoy.mathero.ui.validator.SoloResultValidator;
import com.enjoy.mathero.ui.validator.UserValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "User Controller", description = "Endpoints connected with users")
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ResultService resultService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    MaxStageValidator maxStageValidator;

    @Autowired
    SoloResultValidator soloResultValidator;

    @ApiOperation(value = "Return user with provided id")
    @GetMapping(path="/{userId}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public UserRest getUser(
            @ApiParam(value = "User id from which user object will be retrieved", required = true)  @PathVariable String userId){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);

        returnValue.setClassId(userDto.getClassDetails().getClassId());
        returnValue.setClassName(userDto.getClassDetails().getClassName());

        return returnValue;
    }

    @ApiOperation(value = "Create new user")
    @PostMapping
    public UserRest createUser(
            @ApiParam(value = "User details to store in the database") @RequestBody UserDetailsRequestModel userDetails, BindingResult result){
        userValidator.validate(userDetails, result);

        if(result.hasErrors())
            throw new InvalidRequestBodyException(result);

        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto, "ROLE_STUDENT", userDetails.getClassName());
        BeanUtils.copyProperties(createdUser, returnValue);
        returnValue.setClassName(createdUser.getClassDetails().getClassName());

        return returnValue;
    }

    @ApiOperation(value = "Update user with provided id")
    @PutMapping(path="/{userId}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public UserRest updateUser(
            @ApiParam(value = "Id of user that will be updated", required = true) @PathVariable String userId,
            @ApiParam(value = "User details to be updated", required = true) @RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(userId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @ApiOperation(value = "Delete user with provided id")
    @DeleteMapping(path = "/{userId}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public OperationStatusModel deleteUser(
            @ApiParam(value = "Id of user that will be deleted", required = true) @PathVariable String userId){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");

        userService.deleteUser(userId);

        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @ApiOperation(value = "View the list of users")
    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<UserRest> getUsers(
            @ApiParam(value = "Number of page") @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "Limit of results per page") @RequestParam(value = "limit", defaultValue = "25") int limit){
        CustomList<UserRest> returnValue = new CustomList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto: users){
            UserRest userRest = new UserRest();
            if(userDto.getClassDetails() != null)
                userRest.setClassName(userDto.getClassDetails().getClassName());
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

    @ApiOperation(value = "View the list of solo results for user with provided id")
    @GetMapping(path = "/{userId}/results")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<SoloResultRest> getUserSoloResults(
            @ApiParam(value = "User id for which the list will be retrieved", required = true) @PathVariable String userId){
        CustomList<SoloResultRest> returnValue = new CustomList<>();

        List<SoloResultDto> soloResultDtos = resultService.getSoloResultsByUserId(userId);

        ModelMapper modelMapper = new ModelMapper();

        for(SoloResultDto soloResultDto: soloResultDtos){
            SoloResultRest soloResultRest = new SoloResultRest();
            BeanUtils.copyProperties(soloResultDto, soloResultRest);
            soloResultRest.setUserId(userId);
            returnValue.add(soloResultRest);
        }

        return returnValue;

    }

    @ApiOperation(value = "Set max stage that user with provided id can play")
    @PostMapping(path = "/{userId}/max-stage")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public OperationStatusModel setUserMaxStage(
            @ApiParam(value = "New max stage", required = true) @RequestBody MaxStageRequestModel maxStageRequestModel,
            @ApiParam(value = "User id for which max stage will be updated", required = true) @PathVariable String userId,
            BindingResult result){
        maxStageValidator.validate(maxStageRequestModel, result);

        if(result.hasErrors())
            throw new InvalidRequestBodyException(result);

        OperationStatusModel returnValue = new OperationStatusModel();

        UserDto userDto = userService.getUserByUserId(userId);

        userDto.setMaxStageCanPlay(maxStageRequestModel.getMaxStageCanPlay());
        userService.updateUser(userId, userDto);

        returnValue.setOperationResult("SUCCESS");
        returnValue.setOperationName("MAX_STAGE_NUMBER");

        return returnValue;
    }

    @ApiOperation(value = "Create result for user with provided id")
    @PostMapping(path = "/{userId}/results")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public SoloResultRest createResult(
            @ApiParam(value = "User id for which result will be created", required = true) @PathVariable String userId,
            @ApiParam(value = "Solo result details to store in the database", required = true) @RequestBody SoloResultRequestModel resultDetails,
            BindingResult result){
        soloResultValidator.validate(resultDetails, result);

        if(result.hasErrors())
            throw new InvalidRequestBodyException(result);

        SoloResultRest returnValue = new SoloResultRest();

        UserDto userDto = userService.getUserByUserId(userId);

        SoloResultDto soloResultDto = new SoloResultDto();
        soloResultDto.setUserDetails(userDto);
        BeanUtils.copyProperties(resultDetails, soloResultDto);

        SoloResultDto createdResult = resultService.createSoloResult(soloResultDto);
        BeanUtils.copyProperties(createdResult, returnValue);
        returnValue.setUserId(userDto.getUserId());

        return returnValue;
    }

    @ApiOperation(value = "Return all summary reports for user with provided id")
    @GetMapping(path = "/{userId}/summary-report-all")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<StageSummaryReportRest> getAllStageSummaryReport(
            @ApiParam(value = "User id for which reports will be generated", required = true) @PathVariable String userId){
        CustomList<StageSummaryReportRest> returnValue = new CustomList<>();

        UserDto userDto = userService.getUserByUserId(userId);

        List<StageSummaryReportDto> results = resultService.getAllStagesReportsByUserId(userId);

        for(StageSummaryReportDto dto: results){
            StageSummaryReportRest rest = new StageSummaryReportRest();
            BeanUtils.copyProperties(dto, rest);
            returnValue.add(rest);
        }

        return returnValue;

    }

    @ApiOperation(value = "Return specified stage summary report for class with provided id")
    @GetMapping(path = "/{userId}/summary-report")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public StageSummaryReportRest getStageSummaryReport(
            @ApiParam(value = "Stage number for which summary report will be generated", required = true) @RequestParam(name = "stageNumber") int stageNumber,
            @ApiParam(value = "Class id for which summary report will be generated", required = true) @PathVariable String userId){
        StageSummaryReportRest returnValue = new StageSummaryReportRest();

        UserDto userDto = userService.getUserByUserId(userId);

        StageSummaryReportDto stageSummaryReportDto = resultService.getStageSummaryReportByUserId(userId, stageNumber);
        BeanUtils.copyProperties(stageSummaryReportDto, returnValue);

        return returnValue;

    }

}
