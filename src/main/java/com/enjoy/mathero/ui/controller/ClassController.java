package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.MapUtils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.ClassRequestModel;
import com.enjoy.mathero.ui.model.response.ClassRest;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import com.enjoy.mathero.ui.model.response.StudentRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        returnValue.setClassId(saved.getClassId());
        returnValue.setTeacherId(saved.getTeacherDetails().getUserId());
        returnValue.setTeacherFirstName(saved.getTeacherDetails().getFirstName());
        returnValue.setTeacherLastName(saved.getTeacherDetails().getLastName());
        returnValue.setStudents(new ArrayList<>());

        return returnValue;
    }

    @GetMapping
    public CustomList<ClassRest> getAllClasses(){
        CustomList<ClassRest> returnValue = new CustomList<>();

        List<ClassDto> classDtos = classService.getClasses();
        for(ClassDto classDto: classDtos){
            returnValue.add(MapUtils.classDtoToClassRest(classDto));
        }

        return returnValue;
    }

    @GetMapping(path = "/{classId}")
    public ClassRest getClassById(@PathVariable String classId){
        ClassRest returnValue;

        ClassDto classDto = classService.getClassByClassId(classId);

        returnValue = MapUtils.classDtoToClassRest(classDto);
        returnValue.setClassId(classDto.getClassId());

        return returnValue;
    }

}
