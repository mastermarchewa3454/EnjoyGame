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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ClassService interface. Business logic to deal with classes.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    /**
     * Creates class and calls ClassRepository to save it in the database
     *
     * @param classDto class details
     * @param teacherId id of the teacher
     * @return details stored in the database
     */
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

    /**
     * Returns class form database by class name
     * @param className name of the class
     * @return class details
     */
    @Override
    public ClassDto getClassByClassName(String className) {
        ClassDto returnValue;

        ClassEntity classEntity = classRepository.findByClassName(className);
        if(classEntity == null)
            throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        returnValue = MapUtils.classEntityToClassDto(classEntity);

        return returnValue;
    }

    /**
     * Returns class from database by class id
     * @param classId id of the class
     * @return class details
     */
    @Override
    public ClassDto getClassByClassId(String classId) {
        ClassDto returnValue;

        ClassEntity classEntity = classRepository.findByClassId(classId);
        if(classEntity == null)
            throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        returnValue = MapUtils.classEntityToClassDto(classEntity);

        return returnValue;
    }

    /**
     * Returns oll classes
     * @return list of all class details
     */
    @Override
    public List<ClassDto> getClasses() {
        List<ClassDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        Iterable<ClassEntity> usersPage = classRepository.findAll();

        List<ClassEntity> allEntities = new ArrayList<>();
        usersPage.forEach(allEntities::add);

        for(ClassEntity classEntity: allEntities){

            returnValue.add(MapUtils.classEntityToClassDto(classEntity));

        }

        return returnValue;
    }
}
