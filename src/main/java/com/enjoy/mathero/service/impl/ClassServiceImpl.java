package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.ClassServiceException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.shared.MapUtils;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public ClassDto create(ClassDto classDto, String teacherId) {
        ClassDto returnValue = new ClassDto();

        ModelMapper modelMapper = new ModelMapper();
        ClassEntity toSave = new ClassEntity();
        BeanUtils.copyProperties(classDto, toSave);
        toSave.setClassId(utils.generateClassId(30));
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
        ClassDto returnValue;

        ClassEntity classEntity = classRepository.findByClassName(className);
        if(classEntity == null)
            throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        returnValue = MapUtils.classEntityToClassDto(classEntity);

        return returnValue;
    }

    @Override
    public ClassDto getClassByClassId(String classId) {
        ClassDto returnValue;

        ClassEntity classEntity = classRepository.findByClassId(classId);
        if(classEntity == null)
            throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        returnValue = MapUtils.classEntityToClassDto(classEntity);

        return returnValue;
    }

    @Override
    public List<ClassDto> getClasses() {
        List<ClassDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        Pageable pageableRequest = PageRequest.of(0, 1000);

        Page<ClassEntity> usersPage = classRepository.findAll(pageableRequest);
        List<ClassEntity> allEntities = usersPage.getContent();

        for(ClassEntity classEntity: allEntities){

            returnValue.add(MapUtils.classEntityToClassDto(classEntity));

        }

        return returnValue;
    }
}
