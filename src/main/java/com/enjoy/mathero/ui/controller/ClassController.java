package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.ClassRequestModel;
import com.enjoy.mathero.ui.model.response.ClassRest;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "classes")
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    UserService userService;

    @PostMapping
    public ClassRest createClass(@RequestBody ClassRequestModel classRequestModel){
        ClassRest returnValue = new ClassRest();

        ClassDto classDto = new ClassDto();
        classDto.setClassName(classRequestModel.getClassName());
        ClassDto saved = classService.create(classDto, classRequestModel.getTeacherId());

        returnValue.setClassName(saved.getClassName());
        returnValue.setTeacher(saved.getTeacherDetails().getFirstName() + saved.getTeacherDetails().getLastName());
        returnValue.setStudents(new ArrayList<>());

        return returnValue;
    }

}
