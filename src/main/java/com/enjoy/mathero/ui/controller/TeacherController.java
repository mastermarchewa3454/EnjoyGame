package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.TeacherDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "teachers")
public class TeacherController {

    @Autowired
    UserService userService;

    @PostMapping
    public UserRest createTeacher(@RequestBody TeacherDetailsRequestModel teacherDetailsRequestModel){
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(teacherDetailsRequestModel, userDto);

        UserDto createdUser = userService.createUser(userDto, "ROLE_TEACHER", null);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

}
