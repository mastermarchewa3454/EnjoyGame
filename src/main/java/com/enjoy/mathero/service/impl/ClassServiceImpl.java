package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.response.ErrorMessage;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ClassDto create(ClassDto classDto, String teacherId) {
        ClassDto returnValue = new ClassDto();

        ModelMapper modelMapper = new ModelMapper();
        ClassEntity toSave = new ClassEntity();
        BeanUtils.copyProperties(classDto, toSave);

        UserEntity teacher = userRepository.findByUserId(teacherId);
        if(teacher == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        toSave.setTeacherDetails(teacher);
        ClassEntity saved = classRepository.save(toSave);

        UserDto teacherDto = modelMapper.map(saved.getTeacherDetails(), UserDto.class);
        returnValue = modelMapper.map(saved, ClassDto.class);
        returnValue.setTeacherDetails(teacherDto);

        return returnValue;
    }

    @Override
    public ClassDto getClassByClassName(String className) {
        return null;
    }

    @Override
    public List<ClassDto> getClasses() {
        List<ClassDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        Iterable<ClassEntity> allEntities = classRepository.findAll();

        for(ClassEntity classEntity: allEntities){
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
            returnValue.add(classDto);
        }

        return returnValue;
    }
}
