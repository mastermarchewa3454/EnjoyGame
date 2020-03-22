package com.enjoy.mathero.shared;

import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.response.ClassRest;
import com.enjoy.mathero.ui.model.response.StudentRest;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MapUtils {
    public static ClassRest classDtoToClassRest(ClassDto classDto){
        ClassRest classRest = new ClassRest();
        classRest.setClassName(classDto.getClassName());
        classRest.setTeacher(classDto.getTeacherDetails().getFirstName() + " " + classDto.getTeacherDetails().getLastName());
        List<StudentRest> students = new ArrayList<>();
        List<UserDto> studentsDto = classDto.getStudents();
        for(int i =0;i<studentsDto.size();i++){
            StudentRest studentRest = new StudentRest();
            BeanUtils.copyProperties(studentsDto.get(i),studentRest);
            students.add(studentRest);
        }
        classRest.setStudents(students);

        return classRest;
    }

    public static ClassDto classEntityToClassDto(ClassEntity classEntity){
        ClassDto classDto = new ClassDto();
        UserDto teacher = new UserDto();
        BeanUtils.copyProperties(classEntity.getTeacherDetails(), teacher);
        List<UserDto> students = new ArrayList<>();
        for(UserEntity userEntity: classEntity.getStudents()){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            students.add(userDto);
        }
        classDto.setTeacherDetails(teacher);
        classDto.setStudents(students);
        BeanUtils.copyProperties(classEntity, classDto);

        return classDto;
    }
}
