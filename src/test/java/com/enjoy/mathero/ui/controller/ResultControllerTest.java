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

class ResultControllerTest {

    @Mock
    UserService userService;

    @Mock
    ResultService resultService;

    @Mock
    ClassService classService;

    @Mock
    SoloResultValidator soloResultValidator;

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
    StageSummaryReportDto stageSummaryReportDto;
    List<StageSummaryReportDto> stageSummaryReportDtoList;
    ClassStageSummaryDto classStageSummaryDto;
    List<ClassStageSummaryDto> classStageSummaryDtoList;
    ClassDto classDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = getStudentDto();
        soloResultDto = getSoloResultDto();
        soloResultDtoList = getSoloResultDtoList();
        soloResultRequestModel = getSoloResultRequestModel();
        duoResultDto = getDuoResultDto();
        duoResultDtoList = getDuoResultDtoList();
        duoResultRequestModel = getDuoResultRequestModel();
        stageSummaryReportDto = getStageSummaryDto();
        stageSummaryReportDtoList = getStageSummaryReportDtoList();
        classStageSummaryDto = getClassStageSummaryDto();
        classStageSummaryDtoList = getClassStageSummaryDtoList();
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
    void createResult() {
        BindingResult bindingResult = new BeanPropertyBindingResult(soloResultRequestModel, "resultDetails");
        doNothing().when(soloResultValidator).validate(any(SoloResultRequestModel.class), any(Errors.class));
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(resultService.createSoloResult(any(SoloResultDto.class))).thenReturn(soloResultDto);

        SoloResultRest savedDetails = resultController.createResult(userId, soloResultRequestModel, bindingResult);

        assertNotNull(savedDetails);
        assertEquals(soloResultRequestModel.getEasyCorrect(), savedDetails.getEasyCorrect());
        assertEquals(soloResultRequestModel.getEasyTotal(), savedDetails.getEasyTotal());
        assertEquals(soloResultRequestModel.getMediumCorrect(), savedDetails.getMediumCorrect());
        assertEquals(soloResultRequestModel.getMediumTotal(), savedDetails.getMediumTotal());
        assertEquals(soloResultRequestModel.getHardCorrect(), savedDetails.getHardCorrect());
        assertEquals(soloResultRequestModel.getHardTotal(), savedDetails.getHardTotal());
        assertEquals(soloResultRequestModel.getStageNumber(), savedDetails.getStageNumber());
        assertEquals(soloResultRequestModel.getScore(), savedDetails.getScore());
        assertEquals(userId, savedDetails.getUserId());

    }

    @Test
    void createResult_InvalidRequestBodyException(){
        BindingResult bindingResult = new BeanPropertyBindingResult(soloResultRequestModel, "resultDetails");
        bindingResult.rejectValue("score", "test");
        doNothing().when(soloResultValidator).validate(any(SoloResultRequestModel.class), any(Errors.class));

        assertThrows(InvalidRequestBodyException.class, () -> resultController.createResult(userId, soloResultRequestModel, bindingResult));

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

    @Test
    void getStageSummaryReport() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(resultService.getStageSummaryReportByUserId(anyString(), anyInt())).thenReturn(stageSummaryReportDto);

        StageSummaryReportRest returnedValue = resultController.getStageSummaryReport(3, userId);

        assertNotNull(returnedValue);
        assertEquals(stageSummaryReportDto.getEasyCorrect(), returnedValue.getEasyCorrect());
        assertEquals(stageSummaryReportDto.getEasyTotal(), returnedValue.getEasyTotal());
        assertEquals(stageSummaryReportDto.getMediumCorrect(), returnedValue.getMediumCorrect());
        assertEquals(stageSummaryReportDto.getMediumTotal(), returnedValue.getMediumTotal());
        assertEquals(stageSummaryReportDto.getHardCorrect(), returnedValue.getHardCorrect());
        assertEquals(stageSummaryReportDto.getHardTotal(), returnedValue.getHardTotal());
        assertEquals(stageSummaryReportDto.getStageNumber(), returnedValue.getStageNumber());
        assertEquals(stageSummaryReportDto.getUserId(), returnedValue.getUserId());

    }

    @Test
    void getAllStageSummaryReport() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(resultService.getAllStagesReportsByUserId(anyString())).thenReturn(stageSummaryReportDtoList);

        CustomList<StageSummaryReportRest> returnedValue = resultController.getAllStageSummaryReport(userId);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(stageSummaryReportDtoList.size(), returnedValue.getWrapperList().size());

    }

    @Test
    void getClassStageSummary() {
        when(classService.getClassByClassId(anyString())).thenReturn(classDto);
        when(resultService.getClassStageSummaryByClassId(anyString(), anyInt())).thenReturn(classStageSummaryDto);

        ClassStageSummaryRest returnedValue = resultController.getClassStageSummary(3, "ASARhaja4ad");

        assertNotNull(returnedValue);
        assertEquals(classStageSummaryDto.getEasyCorrect(), returnedValue.getEasyCorrect());
        assertEquals(classStageSummaryDto.getEasyTotal(), returnedValue.getEasyTotal());
        assertEquals(classStageSummaryDto.getMediumCorrect(), returnedValue.getMediumCorrect());
        assertEquals(classStageSummaryDto.getMediumTotal(), returnedValue.getMediumTotal());
        assertEquals(classStageSummaryDto.getHardCorrect(), returnedValue.getHardCorrect());
        assertEquals(classStageSummaryDto.getHardTotal(), returnedValue.getHardTotal());
        assertEquals(classStageSummaryDto.getStageNumber(), returnedValue.getStageNumber());
        assertEquals(classStageSummaryDto.getClassName(), returnedValue.getClassName());
        assertEquals(classStageSummaryDto.getClassId(), returnedValue.getClassId());

    }

    @Test
    void getAllClassStageSummary() {
        when(classService.getClassByClassId(anyString())).thenReturn(classDto);
        when(resultService.getAllClassStageSummaryByClassId(anyString())).thenReturn(classStageSummaryDtoList);

        CustomList<ClassStageSummaryRest> returnedValue = resultController.getAllClassStageSummary("ASDKHa5ajksdh");

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(classStageSummaryDtoList.size(), returnedValue.getWrapperList().size());
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

    private SoloResultRequestModel getSoloResultRequestModel(){
        SoloResultRequestModel soloResultRequestModel = new SoloResultRequestModel();
        soloResultRequestModel.setEasyCorrect(10);
        soloResultRequestModel.setEasyTotal(12);
        soloResultRequestModel.setHardCorrect(5);
        soloResultRequestModel.setHardTotal(7);
        soloResultRequestModel.setMediumCorrect(9);
        soloResultRequestModel.setMediumTotal(13);
        soloResultRequestModel.setScore(100);
        soloResultRequestModel.setStageNumber(3);

        return soloResultRequestModel;
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

    private StageSummaryReportDto getStageSummaryDto(){
        StageSummaryReportDto stageSummaryReportDto = new StageSummaryReportDto();
        stageSummaryReportDto.setEasyCorrect(2);
        stageSummaryReportDto.setEasyTotal(5);
        stageSummaryReportDto.setMediumCorrect(5);
        stageSummaryReportDto.setMediumTotal(8);
        stageSummaryReportDto.setHardCorrect(6);
        stageSummaryReportDto.setHardTotal(10);
        stageSummaryReportDto.setStageNumber(3);
        stageSummaryReportDto.setUserId(userId);

        return stageSummaryReportDto;
    }

    private List<StageSummaryReportDto> getStageSummaryReportDtoList(){
        List<StageSummaryReportDto> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(stageSummaryReportDto);
        }

        return list;
    }

    private ClassStageSummaryDto getClassStageSummaryDto(){
        ClassStageSummaryDto classStageSummaryDto = new ClassStageSummaryDto();
        classStageSummaryDto.setEasyCorrect(2);
        classStageSummaryDto.setEasyTotal(5);
        classStageSummaryDto.setMediumCorrect(5);
        classStageSummaryDto.setMediumTotal(8);
        classStageSummaryDto.setHardCorrect(6);
        classStageSummaryDto.setHardTotal(10);
        classStageSummaryDto.setStageNumber(3);
        classStageSummaryDto.setClassName("Class A");
        classStageSummaryDto.setClassId("ASLKDha5hakjS");

        return classStageSummaryDto;
    }

    private List<ClassStageSummaryDto> getClassStageSummaryDtoList(){
        List<ClassStageSummaryDto> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(classStageSummaryDto);
        }

        return list;
    }
}