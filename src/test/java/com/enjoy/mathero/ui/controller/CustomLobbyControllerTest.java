package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.InvalidRequestBodyException;
import com.enjoy.mathero.service.CustomLobbyService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.CustomLobbyDto;
import com.enjoy.mathero.shared.dto.QuestionDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.CustomLobbyRequestModel;
import com.enjoy.mathero.ui.model.request.QuestionRequestModel;
import com.enjoy.mathero.ui.model.response.CustomLobbyRest;
import com.enjoy.mathero.ui.validator.CustomLobbyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

/**
 * Unit tests for CustomLobbyController class
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
class CustomLobbyControllerTest {

    @Mock
    CustomLobbyService customLobbyService;

    @Mock
    UserService userService;

    @Mock
    CustomLobbyValidator customLobbyValidator;

    @InjectMocks
    CustomLobbyController customLobbyController;

    CustomLobbyRequestModel customLobbyRequestModel;
    QuestionRequestModel questionRequestModel;
    QuestionDto questionDto;
    CustomLobbyDto customLobbyDto;
    UserDto userDto;
    String userId = "ASKJH5ahjbda";
    String lobbyId = "ALSKh5jasbd";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = getStudentDto();
        questionRequestModel = getQuestionRequestModel();
        customLobbyRequestModel = getCustomLobbyRequestModel();
        questionDto = getQuestionDto();
        customLobbyDto = getCustomLobbyDto();
    }

    @Test
    void createCustomLobby() {
        BindingResult bindingResult = new BeanPropertyBindingResult(customLobbyRequestModel, "lobbyDetails");
        doNothing().when(customLobbyValidator).validate(customLobbyRequestModel, bindingResult);
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        when(customLobbyService.createCustomLobby(any(CustomLobbyDto.class))).thenReturn(customLobbyDto);

        CustomLobbyRest savedDetails = customLobbyController.createCustomLobby(customLobbyRequestModel, bindingResult);

        assertNotNull(savedDetails);
        assertNotNull(savedDetails.getQuestions());
        assertEquals(customLobbyRequestModel.getQuestions().size(), savedDetails.getQuestions().size());
        assertEquals(customLobbyRequestModel.getAuthorId(), savedDetails.getAuthorId());
        assertEquals(lobbyId, savedDetails.getLobbyId());

    }

    @Test
    void createCustomLobby_InvalidRequestBodyException() {
        BindingResult bindingResult = new BeanPropertyBindingResult(customLobbyRequestModel, "lobbyDetails");
        bindingResult.rejectValue("authorId", "test");
        doNothing().when(customLobbyValidator).validate(customLobbyRequestModel, bindingResult);

        assertThrows(InvalidRequestBodyException.class, () -> customLobbyController.createCustomLobby(customLobbyRequestModel, bindingResult));

    }

    @Test
    void getCustomLobbyByLobbyId() {
        when(customLobbyService.getCustomLobbyByLobbyId(anyString())).thenReturn(customLobbyDto);

        CustomLobbyRest returnedValue = customLobbyController.getCustomLobbyByLobbyId(userId);

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getQuestions());
        assertEquals(customLobbyDto.getQuestions().size(), returnedValue.getQuestions().size());
        assertEquals(customLobbyDto.getAuthorDetails().getUserId(), returnedValue.getAuthorId());
        assertEquals(lobbyId, returnedValue.getLobbyId());

    }

    private CustomLobbyRequestModel getCustomLobbyRequestModel(){
        CustomLobbyRequestModel customLobbyRequestModel = new CustomLobbyRequestModel();
        customLobbyRequestModel.setAuthorId(userId);
        List<QuestionRequestModel> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(questionRequestModel);
        }
        customLobbyRequestModel.setQuestions(list);

        return customLobbyRequestModel;
    }

    private QuestionRequestModel getQuestionRequestModel(){
        QuestionRequestModel questionRequestModel = new QuestionRequestModel();
        questionRequestModel.setContent("Test content");
        questionRequestModel.setCorrectAnswer("Correct answer");

        return questionRequestModel;
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

    private CustomLobbyDto getCustomLobbyDto(){
        CustomLobbyDto customLobbyDto = new CustomLobbyDto();
        customLobbyDto.setAuthorDetails(userDto);
        customLobbyDto.setLobbyId(lobbyId);
        List<QuestionDto> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(questionDto);
        }
        customLobbyDto.setQuestions(list);

        return customLobbyDto;
    }

    private QuestionDto getQuestionDto(){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setContent("Test content");
        questionDto.setCorrectAnswer("Correct answer");

        return questionDto;
    }
}