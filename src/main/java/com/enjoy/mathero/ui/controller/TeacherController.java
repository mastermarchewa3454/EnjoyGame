package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.TeacherDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.TeacherRest;
import com.enjoy.mathero.ui.model.response.UserRest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Teacher Controller", description = "Endpoints connected with teachers")
@RestController
@RequestMapping(path = "teachers")
public class TeacherController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Create new teacher")
    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public UserRest createTeacher(
            @ApiParam(value = "Teacher details to store in the database")  @RequestBody TeacherDetailsRequestModel teacherDetailsRequestModel){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(teacherDetailsRequestModel, userDto);

        UserDto createdUser = userService.createUser(userDto, "ROLE_TEACHER", null);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @ApiOperation(value = "Return teacher with provided id")
    @GetMapping(path = "/{userId}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public TeacherRest getTeacherDetails(
            @ApiParam(value = "User id from which teacher object will be retrieved") @PathVariable String userId){
        TeacherRest returnValue = new TeacherRest();

        UserDto userDto = userService.getTeacherByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);

        returnValue.setTeachClassId(userDto.getTeachClasses().getClassId());
        returnValue.setTeachClassName(userDto.getTeachClasses().getClassName());

        return returnValue;
    }

}
