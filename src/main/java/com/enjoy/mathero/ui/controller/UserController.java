package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.InvalidRequestBodyException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.MaxStageRequestModel;
import com.enjoy.mathero.ui.model.request.UserDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.*;
import com.enjoy.mathero.ui.validator.MaxStageValidator;
import com.enjoy.mathero.ui.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    
    @GetMapping(path="/{userId}")
    public UserRest getUser(@PathVariable String userId){
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);

        returnValue.setClassId(userDto.getClassDetails().getClassId());
        returnValue.setClassName(userDto.getClassDetails().getClassName());

        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails, BindingResult result){
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

    @PutMapping(path="/{userId}")
    public UserRest updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(userId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{userId}")
    public OperationStatusModel deleteUser(@PathVariable String userId){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("DELETE");

        userService.deleteUser(userId);

        returnValue.setOperationResult("SUCCESS");
        return returnValue;
    }

    @GetMapping
    public CustomList<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit){
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

    @GetMapping(path = "/{userId}/results")
    public CustomList<SoloResultRest> getUserSoloResults(@PathVariable String userId){
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

    @PostMapping(path = "/{userId}/max-stage")
    public OperationStatusModel setUserMaxStage(@RequestBody MaxStageRequestModel maxStageRequestModel, @PathVariable String userId,
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

}
