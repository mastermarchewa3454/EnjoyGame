package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.ClassServiceException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

/**
 * Unit tests for ClassServiceImpl class
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
class ClassServiceImplTest {

    @Mock
    ClassRepository classRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @InjectMocks
    ClassServiceImpl classService;

    String publicUserId = "ASKJDHAL5akjsh";
    String publicClassId = "akjsdhajk4Ashjra";
    UserEntity teacherEntity;
    ClassEntity classEntity;
    ClassDto classDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        teacherEntity = getTeacherEntity();
        classEntity = getClassEntity();
        classDto = getClassDto();
    }

    @Test
    void create() {
        when(userRepository.findByUserId(anyString())).thenReturn(teacherEntity);
        when(utils.generateClassId(anyInt())).thenReturn(publicClassId);
        when(classRepository.save(any(ClassEntity.class))).thenReturn(classEntity);

        ClassDto storedValues = classService.create(classDto, "AJSKHa5jahksd");

        assertNotNull(storedValues);
        assertNotNull(storedValues.getTeacherDetails());
        assertEquals(classDto.getClassName(), storedValues.getClassName());
        assertEquals(classDto.getClassId(), storedValues.getClassId());

    }

    @Test
    void create_UserServiceException(){
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, () -> classService.create(classDto, "ASJKdga5"));

    }

    @Test
    void getClassByClassName() {
        classEntity.setTeacherDetails(teacherEntity);
        when(classRepository.findByClassName(anyString())).thenReturn(classEntity);

        ClassDto returnedValue = classService.getClassByClassName("Class A");

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getTeacherDetails());
        assertEquals(classEntity.getClassName(), returnedValue.getClassName());
        assertEquals(classEntity.getClassId(), returnedValue.getClassId());
        assertEquals(classEntity.getStudents().size(), returnedValue.getStudents().size());

    }

    @Test
    void getClassByClassName_ClassServiceException(){
        when(classRepository.findByClassName(anyString())).thenReturn(null);

        assertThrows(ClassServiceException.class, () -> classService.getClassByClassName("Class A"));

    }

    @Test
    void getClassByClassId() {
        classEntity.setTeacherDetails(teacherEntity);
        when(classRepository.findByClassId(anyString())).thenReturn(classEntity);

        ClassDto returnedValue = classService.getClassByClassId("AKJS5ajS");

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getTeacherDetails());
        assertEquals(classEntity.getClassName(), returnedValue.getClassName());
        assertEquals(classEntity.getClassId(), returnedValue.getClassId());
        assertEquals(classEntity.getStudents().size(), returnedValue.getStudents().size());
    }

    @Test
    void getClassByClassId_ClassServiceException() {
        when(classRepository.findByClassId(anyString())).thenReturn(null);

        assertThrows(ClassServiceException.class, () -> classService.getClassByClassId("ASKJRH5a5"));

    }

    @Test
    void getClasses() {
        List<ClassEntity> classEntityList = getListClassEntity();
        when(classRepository.findAll()).thenReturn(classEntityList);

        List<ClassDto> returnedValue = classService.getClasses();

        assertNotNull(returnedValue);
        assertEquals(classEntityList.size(), returnedValue.size());

    }

    private UserEntity getTeacherEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("teacher");
        userEntity.setLastName("teacher");
        userEntity.setUsername("test");
        userEntity.setUserId(publicUserId);

        return userEntity;
    }

    private ClassEntity getClassEntity(){
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassId(publicClassId);
        classEntity.setClassName("Class A");
        classEntity.setTeacherDetails(getTeacherEntity());
        classEntity.setStudents(Arrays.asList(new UserEntity(), new UserEntity(), new UserEntity()));

        return classEntity;
    }

    private ClassDto getClassDto(){
        ClassDto classDto = new ClassDto();
        classDto.setClassId(publicClassId);
        classDto.setClassName("Class A");
        classDto.setStudents(Arrays.asList(new UserDto(), new UserDto(), new UserDto()));

        return classDto;
    }

    private List<ClassEntity> getListClassEntity(){
        List<ClassEntity> classEntities = new ArrayList<>();
        for(int i=0;i<5;i++){
            classEntities.add(getClassEntity());
        }

        return classEntities;
    }
}