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
        returnValue.setTeacher(saved.getTeacherDetails().getFirstName() + saved.getTeacherDetails().getLastName());
        returnValue.setStudents(new ArrayList<>());

        return returnValue;
    }

    @GetMapping
    public List<ClassRest> getAllClasses(){
        List<ClassRest> returnValue = new ArrayList<>();

        List<ClassDto> classDtos = classService.getClasses();
        for(ClassDto classDto: classDtos){
            ClassRest classRest = new ClassRest();
            classRest.setClassName(classDto.getClassName());
            classRest.setTeacher(classDto.getTeacherDetails().getFirstName() + " " + classDto.getTeacherDetails().getLastName());
            List<StudentRest> students = new ArrayList<>();
            for(UserDto student:classDto.getStudents()){
                StudentRest studentRest = new StudentRest();
                BeanUtils.copyProperties(student, studentRest);
                students.add(studentRest);
            }
            classRest.setStudents(students);
            returnValue.add(classRest);
        }

        return returnValue;
    }

}
