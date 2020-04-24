package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.InvalidRequestBodyException;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.*;
import com.enjoy.mathero.ui.model.request.DuoResultRequestModel;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.response.ClassStageSummaryRest;
import com.enjoy.mathero.ui.model.response.DuoResultRest;
import com.enjoy.mathero.ui.model.response.SoloResultRest;
import com.enjoy.mathero.ui.model.response.StageSummaryReportRest;
import com.enjoy.mathero.ui.validator.DuoResultValidator;
import com.enjoy.mathero.ui.validator.SoloResultValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

/**
 * Unit tests for ResultController class
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
class ResultControllerTest {

    @Mock
    UserService userService;

    @Mock
    ResultService resultService;

    @Mock
    ClassService classService;

    @Mock
    DuoResultValidator duoResultValidator;

    @InjectMocks
    ResultController resultController;

    String userId = "ASKJHd4a5ajkshd";
    String userId2 = "AslkdjaasDKJHKJhdas";
    UserDto userDto;
    SoloResultDto soloResultDto;
    List<SoloResultDto> soloResultDtoList;
    DuoResultDto duoResultDto;
    List<DuoResultDto> duoResultDtoList;
    SoloResultRequestModel soloResultRequestModel;
    DuoResultRequestModel duoResultRequestModel;
    ClassStageSummaryDto classStageSummaryDto;
    List<ClassStageSummaryDto> classStageSummaryDtoList;
    ClassDto classDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = getStudentDto();
        soloResultDto = getSoloResultDto();
        soloResultDtoList = getSoloResultDtoList();
        duoResultDto = getDuoResultDto();
        duoResultDtoList = getDuoResultDtoList();
        duoResultRequestModel = getDuoResultRequestModel();
        classDto = getClassDto();
    }

    @Test
    void getTop20_stageNumberNotSpecified() {
        when(resultService.getTop20()).thenReturn(soloResultDtoList);

        CustomList<SoloResultRest> returnedValue = resultController.getTop20(null);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(soloResultDtoList.size(), returnedValue.getWrapperList().size());
        assertEquals(soloResultDtoList.get(0).getUserDetails().getUserId(), returnedValue.getWrapperList().get(0).getUserId());

    }

    @Test
    void getTop20_stageNumberSpecified() {
        when(resultService.getTop20(anyInt())).thenReturn(soloResultDtoList);

        CustomList<SoloResultRest> returnedValue = resultController.getTop20(3);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(soloResultDtoList.size(), returnedValue.getWrapperList().size());
        assertEquals(soloResultDtoList.get(0).getUserDetails().getUserId(), returnedValue.getWrapperList().get(0).getUserId());

    }

    @Test
    void createDuoResult() {
        BindingResult bindingResult = new BeanPropertyBindingResult(duoResultRequestModel, "resultDetails");
        doNothing().when(duoResultValidator).validate(any(DuoResultRequestModel.class), any(Errors.class));
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(resultService.createDuoResult(any(DuoResultDto.class))).thenReturn(duoResultDto);

        DuoResultRest savedDetails = resultController.createDuoResult(userId, userId2, duoResultRequestModel, bindingResult);

        assertNotNull(savedDetails);
        assertEquals(duoResultRequestModel.getStageNumber(), savedDetails.getStageNumber());
        assertEquals(duoResultRequestModel.getScore(), savedDetails.getScore());
        assertEquals(userId, savedDetails.getUserId1());
        assertEquals(userId, savedDetails.getUserId2());

    }

    @Test
    void createDuoResult_InvalidRequestBodyException() {
        BindingResult bindingResult = new BeanPropertyBindingResult(duoResultRequestModel, "resultDetails");
        bindingResult.rejectValue("score", "test");
        doNothing().when(duoResultValidator).validate(any(DuoResultRequestModel.class), any(Errors.class));

        assertThrows(InvalidRequestBodyException.class, () -> resultController.createDuoResult(userId, userId2, duoResultRequestModel, bindingResult));

    }

    @Test
    void getTop20Duo_stageNumberNotSpecified() {
        when(resultService.getTop20Duo()).thenReturn(duoResultDtoList);

        CustomList<DuoResultRest> returnedValue = resultController.getTop20Duo(null);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(duoResultDtoList.size(), returnedValue.getWrapperList().size());
        assertEquals(duoResultDtoList.get(0).getUserDetails1().getUserId(), returnedValue.getWrapperList().get(0).getUserId1());
        assertEquals(duoResultDtoList.get(0).getUserDetails2().getUserId(), returnedValue.getWrapperList().get(0).getUserId2());

    }

    @Test
    void getTop20Duo_stageNumberSpecified() {
        when(resultService.getTop20Duo(anyInt())).thenReturn(duoResultDtoList);

        CustomList<DuoResultRest> returnedValue = resultController.getTop20Duo(3);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(duoResultDtoList.size(), returnedValue.getWrapperList().size());
        assertEquals(duoResultDtoList.get(0).getUserDetails1().getUserId(), returnedValue.getWrapperList().get(0).getUserId1());
        assertEquals(duoResultDtoList.get(0).getUserDetails2().getUserId(), returnedValue.getWrapperList().get(0).getUserId2());

    }

    private SoloResultDto getSoloResultDto(){
        SoloResultDto soloResultDto = new SoloResultDto();

        soloResultDto.setResultId("AAawj4h5aw");
        soloResultDto.setEasyCorrect(10);
        soloResultDto.setEasyTotal(12);
        soloResultDto.setHardCorrect(5);
        soloResultDto.setHardTotal(7);
        soloResultDto.setMediumCorrect(9);
        soloResultDto.setMediumTotal(13);
        soloResultDto.setScore(100);
        soloResultDto.setStageNumber(3);
        soloResultDto.setUserDetails(userDto);

        return soloResultDto;
    }

    private ClassDto getClassDto(){
        ClassDto classDto = new ClassDto();
        classDto.setClassName("Class A");
        classDto.setClassId("ASKDHa5akjhsD");

        return classDto;
    }

    private UserDto getStudentDto(){
        UserDto studentDto = new UserDto();
        studentDto.setUserId(userId);
        studentDto.setUsername("student");
        studentDto.setFirstName("Test");
        studentDto.setLastName("Test");
        studentDto.setPassword("student");
        studentDto.setEmail("test@test.com");

        return studentDto;
    }

    private List<SoloResultDto> getSoloResultDtoList(){
        List<SoloResultDto> list = new ArrayList<>();
        for(int i =0;i<20;i++){
            list.add(soloResultDto);
        }

        return list;
    }

    private DuoResultRequestModel getDuoResultRequestModel(){
        DuoResultRequestModel duoResultRequestModel = new DuoResultRequestModel();
        duoResultRequestModel.setScore(100);
        duoResultRequestModel.setStageNumber(3);

        return duoResultRequestModel;
    }

    private DuoResultDto getDuoResultDto(){
        DuoResultDto duoResultDto = new DuoResultDto();
        duoResultDto.setStageNumber(3);
        duoResultDto.setScore(100);
        duoResultDto.setUserDetails1(userDto);
        duoResultDto.setUserDetails2(userDto);

        return duoResultDto;
    }

    private List<DuoResultDto> getDuoResultDtoList(){
        List<DuoResultDto> list = new ArrayList<>();
        for(int i =0;i<20;i++){
            list.add(duoResultDto);
        }

        return list;
    }

}