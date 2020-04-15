package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.ClassRequestModel;
import com.enjoy.mathero.ui.model.response.ClassRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

class ClassControllerTest {

    @Mock
    ClassService classService;

    @Mock
    UserService userService;

    @InjectMocks
    ClassController classController;

    String classId = "ASJkhr5ajsrlhf";
    String userId = "ASKJHa5lkhs";
    ClassDto classDto;
    ClassRequestModel classRequestModel;
    UserDto teacherDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        teacherDto = getUserDto();
        classDto = getClassDto();
        classRequestModel = getClassRequestModel();
    }

    @Test
    void createClass() {
        when(classService.create(any(ClassDto.class), anyString())).thenReturn(classDto);

        ClassRest savedDetails = classController.createClass(classRequestModel);

        assertNotNull(savedDetails);
        assertEquals(classDto.getClassName(), savedDetails.getClassName());
        assertEquals(classDto.getClassId(), savedDetails.getClassId());
        assertEquals(classDto.getTeacherDetails().getUserId(), savedDetails.getTeacherId());
        assertEquals(classDto.getTeacherDetails().getFirstName(), savedDetails.getTeacherFirstName());
        assertEquals(classDto.getTeacherDetails().getLastName(), savedDetails.getTeacherLastName());

    }

    @Test
    void getAllClasses() {
        classDto.setStudents(Arrays.asList(new UserDto(), new UserDto(), new UserDto()));
        when(classService.getClasses()).thenReturn(Arrays.asList(classDto, classDto, classDto));

        CustomList<ClassRest> returnedValue = classController.getAllClasses();

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(3, returnedValue.getWrapperList().size());

    }

    @Test
    void getClassById() {
        classDto.setStudents(Arrays.asList(new UserDto(), new UserDto(), new UserDto()));
        when(classService.getClassByClassId(anyString())).thenReturn(classDto);

        ClassRest returnedValue = classController.getClassById(classId);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getStudents());
        assertEquals(3, returnedValue.getStudents().size());
        assertEquals(classDto.getTeacherDetails().getUserId(), returnedValue.getTeacherId());
        assertEquals(classDto.getTeacherDetails().getFirstName(), returnedValue.getTeacherFirstName());
        assertEquals(classDto.getTeacherDetails().getLastName(), returnedValue.getTeacherLastName());
        assertEquals(classDto.getClassId(), returnedValue.getClassId());
        assertEquals(classDto.getClassName(), returnedValue.getClassName());

    }

    private ClassDto getClassDto(){
        ClassDto classDto = new ClassDto();
        classDto.setClassName("Class A");
        classDto.setClassId(classId);
        classDto.setTeacherDetails(teacherDto);

        return classDto;
    }

    private UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setFirstName("Test");
        userDto.setLastName("Test");
        userDto.setUsername("teacher");

        return userDto;
    }

    private ClassRequestModel getClassRequestModel(){
        ClassRequestModel classRequestModel = new ClassRequestModel();
        classRequestModel.setClassName("Class A");
        classRequestModel.setTeacherId(userId);

        return classRequestModel;
    }
}