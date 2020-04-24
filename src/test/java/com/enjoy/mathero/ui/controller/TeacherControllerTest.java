package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.TeacherDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.TeacherRest;
import com.enjoy.mathero.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

/**
 * Unit tests for TeacherController class
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
class TeacherControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    TeacherController teacherController;

    TeacherDetailsRequestModel teacherDetailsRequestModel;
    UserDto teacherDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        teacherDetailsRequestModel = getTeacherDetailsRequestModel();
        teacherDto = getTeacherDto();
    }

    @Test
    void createTeacher() {
        when(userService.createUser(any(UserDto.class), anyString(), any())).thenReturn(teacherDto);

        UserRest savedDetails = teacherController.createTeacher(teacherDetailsRequestModel);

        assertNotNull(savedDetails);
        assertNull(savedDetails.getClassName());
        assertEquals(teacherDto.getUsername(), savedDetails.getUsername());
        assertEquals(teacherDto.getFirstName(), savedDetails.getFirstName());
        assertEquals(teacherDto.getLastName(), savedDetails.getLastName());
        assertEquals(teacherDto.getEmail(), savedDetails.getEmail());

    }

    @Test
    void getTeacherDetails() {
        List<ClassDto> classDtos = new ArrayList<>();
        classDtos.add(getClassDto());
        classDtos.add(getClassDto());
        classDtos.add(getClassDto());
        teacherDto.setTeachClasses(classDtos.get(0));

        when(userService.getTeacherByUserId(anyString())).thenReturn(teacherDto);

        TeacherRest returnedValue = teacherController.getTeacherDetails("AJHEGRJH5asdhjag");

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getTeachClassId());
        assertNotNull(returnedValue.getTeachClassName());
        assertEquals(teacherDto.getFirstName(), returnedValue.getFirstName());
        assertEquals(teacherDto.getLastName(), returnedValue.getLastName());

    }

    private TeacherDetailsRequestModel getTeacherDetailsRequestModel(){
        TeacherDetailsRequestModel teacherDetailsRequestModel = new TeacherDetailsRequestModel();
        teacherDetailsRequestModel.setUsername("teacher");
        teacherDetailsRequestModel.setFirstName("Test");
        teacherDetailsRequestModel.setLastName("Test");
        teacherDetailsRequestModel.setPassword("teacher");
        teacherDetailsRequestModel.setEmail("test@test.com");

        return teacherDetailsRequestModel;
    }

    private UserDto getTeacherDto(){
        UserDto teacherDto = new UserDto();
        teacherDto.setUsername("teacher");
        teacherDto.setFirstName("Test");
        teacherDto.setLastName("Test");
        teacherDto.setPassword("teacher");
        teacherDto.setEmail("test@test.com");

        return teacherDto;
    }

    private ClassDto getClassDto(){
        ClassDto classDto = new ClassDto();
        classDto.setClassId("ASJKdhakj5ahsHJD");
        classDto.setClassName("Class A");

        return classDto;
    }
}