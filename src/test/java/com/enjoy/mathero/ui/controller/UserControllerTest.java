package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.InvalidRequestBodyException;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.StageSummaryReportDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.MaxStageRequestModel;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.request.UserDetailsRequestModel;
import com.enjoy.mathero.ui.model.response.OperationStatusModel;
import com.enjoy.mathero.ui.model.response.SoloResultRest;
import com.enjoy.mathero.ui.model.response.StageSummaryReportRest;
import com.enjoy.mathero.ui.model.response.UserRest;
import com.enjoy.mathero.ui.validator.MaxStageValidator;
import com.enjoy.mathero.ui.validator.SoloResultValidator;
import com.enjoy.mathero.ui.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

/**
 * Unit tests for UserController class
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    ResultService resultService;

    @Mock
    UserValidator userValidator;

    @Mock
    MaxStageValidator maxStageValidator;

    @Mock
    SoloResultValidator soloResultValidator;

    @InjectMocks
    UserController userController;

    String userId = "AJKSHeJHG45akjshd";
    String publicClassId = "akjsdhajk4Ashjra";
    UserDto userDto;
    SoloResultDto soloResultDto;
    UserDetailsRequestModel userDetailsRequestModel;
    SoloResultRequestModel soloResultRequestModel;
    StageSummaryReportDto stageSummaryReportDto;
    List<StageSummaryReportDto> stageSummaryReportDtoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = getStudentDto();
        userDetailsRequestModel = getUserDetails();
        soloResultDto = getSoloResultDto();
        soloResultRequestModel = getSoloResultRequestModel();
        stageSummaryReportDto = getStageSummaryDto();
        stageSummaryReportDtoList = getStageSummaryReportDtoList();
    }

    @Test
    void getUser() {
        userDto.setMaxStageCanPlay(3);
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest returnedValue = userController.getUser(userId);

        assertNotNull(returnedValue);
        assertEquals(userDto.getUserId(), returnedValue.getUserId());
        assertEquals(userDto.getUsername(), returnedValue.getUsername());
        assertEquals(userDto.getFirstName(), returnedValue.getFirstName());
        assertEquals(userDto.getLastName(), returnedValue.getLastName());
        assertEquals(userDto.getEmail(), returnedValue.getEmail());
        assertEquals(userDto.getClassDetails().getClassId(), returnedValue.getClassId());
        assertEquals(userDto.getClassDetails().getClassName(), returnedValue.getClassName());
        assertEquals(userDto.getMaxStageCanPlay(), returnedValue.getMaxStageCanPlay());

    }

    @Test
    void createUser() {
        BindingResult bindingResult = new BeanPropertyBindingResult(userDetailsRequestModel, "userDetails");
        doNothing().when(userValidator).validate(any(UserDetailsRequestModel.class),any(Errors.class));
        when(userService.createUser(any(UserDto.class), anyString(), anyString())).thenReturn(userDto);

        UserRest storedValue = userController.createUser(userDetailsRequestModel, bindingResult);

        assertNotNull(storedValue);
        assertEquals(userDetailsRequestModel.getUsername(), storedValue.getUsername());
        assertEquals(userDetailsRequestModel.getFirstName(), storedValue.getFirstName());
        assertEquals(userDetailsRequestModel.getLastName(), storedValue.getLastName());
        assertEquals(userDetailsRequestModel.getEmail(), storedValue.getEmail());
        assertEquals(userDetailsRequestModel.getClassName(), storedValue.getClassName());
        assertEquals(0, storedValue.getMaxStageCanPlay());

    }

    @Test
    void createUser_InvalidRequestBodyException() {
        BindingResult bindingResult = new BeanPropertyBindingResult(userDetailsRequestModel, "userDetails");
        bindingResult.rejectValue("username", "test");
        doNothing().when(userValidator).validate(any(UserDetailsRequestModel.class),any(Errors.class));

        assertThrows(InvalidRequestBodyException.class, () -> userController.createUser(new UserDetailsRequestModel(), bindingResult));

    }

    @Test
    void getUsers() {
        List<UserDto> users = Arrays.asList(userDto, userDto, userDto);

        when(userService.getUsers(anyInt(), anyInt())).thenReturn(users);

        CustomList<UserRest> returnedValue = userController.getUsers(3, 5);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(users.size(), returnedValue.getWrapperList().size());

    }

    @Test
    void getUserSoloResults() {
        List<SoloResultDto> soloResultDtos = Arrays.asList(soloResultDto, soloResultDto, soloResultDto, soloResultDto);

        when(resultService.getSoloResultsByUserId(anyString())).thenReturn(soloResultDtos);

        CustomList<SoloResultRest> returnedValue = userController.getUserSoloResults(userId);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(soloResultDtos.size(), returnedValue.getWrapperList().size());

    }

    @Test
    void setUserMaxStage() {
        MaxStageRequestModel maxStageRequestModel = new MaxStageRequestModel();
        maxStageRequestModel.setMaxStageCanPlay(3);
        BindingResult bindingResult = new BeanPropertyBindingResult(maxStageRequestModel, "maxStageDetails");

        doNothing().when(maxStageValidator).validate(any(MaxStageRequestModel.class), any(Errors.class));
        userDto.setMaxStageCanPlay(2);
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        userDto.setMaxStageCanPlay(4);
        when(userService.updateUser(anyString(), any(UserDto.class))).thenReturn(userDto);

        OperationStatusModel returnValue = userController.setUserMaxStage(maxStageRequestModel, userId, bindingResult);

        assertEquals("SUCCESS", returnValue.getOperationResult());
        assertEquals("MAX_STAGE_NUMBER", returnValue.getOperationName());

    }

    @Test
    void createResult() {
        BindingResult bindingResult = new BeanPropertyBindingResult(soloResultRequestModel, "resultDetails");
        doNothing().when(soloResultValidator).validate(any(SoloResultRequestModel.class), any(Errors.class));
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(resultService.createSoloResult(any(SoloResultDto.class))).thenReturn(soloResultDto);

        SoloResultRest savedDetails = userController.createResult(userId, soloResultRequestModel, bindingResult);

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

        assertThrows(InvalidRequestBodyException.class, () -> userController.createResult(userId, soloResultRequestModel, bindingResult));

    }

    @Test
    void getStageSummaryReport() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(resultService.getStageSummaryReportByUserId(anyString(), anyInt())).thenReturn(stageSummaryReportDto);

        StageSummaryReportRest returnedValue = userController.getStageSummaryReport(3, userId);

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

        CustomList<StageSummaryReportRest> returnedValue = userController.getAllStageSummaryReport(userId);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getWrapperList());
        assertEquals(stageSummaryReportDtoList.size(), returnedValue.getWrapperList().size());

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

    private UserDto getStudentDto(){
        UserDto studentDto = new UserDto();
        studentDto.setUserId(userId);
        studentDto.setUsername("student");
        studentDto.setFirstName("Test");
        studentDto.setLastName("Test");
        studentDto.setPassword("student");
        studentDto.setEmail("test@test.com");
        studentDto.setClassDetails(getClassDto());

        return studentDto;
    }

    private ClassDto getClassDto(){
        ClassDto classDto = new ClassDto();
        classDto.setClassId(publicClassId);
        classDto.setClassName("Class A");

        return classDto;
    }

    private UserDetailsRequestModel getUserDetails(){
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Test");
        userDetailsRequestModel.setLastName("Test");
        userDetailsRequestModel.setClassName("Class A");
        userDetailsRequestModel.setEmail("test@test.com");
        userDetailsRequestModel.setUsername("student");
        userDetailsRequestModel.setPassword("student");

        return userDetailsRequestModel;
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
}