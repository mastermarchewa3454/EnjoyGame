package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.CustomLobbyServiceException;
import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.CustomLobbyEntity;
import com.enjoy.mathero.io.entity.QuestionEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.CustomLobbyRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.CustomLobbyDto;
import com.enjoy.mathero.shared.dto.QuestionDto;
import com.enjoy.mathero.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

class CustomLobbyServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CustomLobbyRepository customLobbyRepository;

    @Mock
    Utils utils;

    @InjectMocks
    CustomLobbyServiceImpl customLobbyService;

    String customLobbyId = "ASjkdh55ajhsbd";
    String publicUserId = "AJKshdaj5agjrs";
    CustomLobbyEntity customLobbyEntity;
    CustomLobbyDto customLobbyDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customLobbyDto = getCustomLobbyDto();
        customLobbyEntity = getCustomLobbyEntity();
    }

    @Test
    void createCustomLobby() {
        when(utils.generateCustomLobbyId(anyInt())).thenReturn(customLobbyId);
        when(customLobbyRepository.save(any(CustomLobbyEntity.class))).thenReturn(customLobbyEntity);

        CustomLobbyDto savedDetails = customLobbyService.createCustomLobby(customLobbyDto);

        assertNotNull(savedDetails);
        assertNotNull(savedDetails.getQuestions());
        assertEquals(customLobbyDto.getQuestions().size(), savedDetails.getQuestions().size());
        assertEquals(customLobbyDto.getLobbyId(), savedDetails.getLobbyId());
        assertNotNull(savedDetails.getAuthorDetails());
        assertEquals(customLobbyDto.getAuthorDetails().getUserId(), savedDetails.getAuthorDetails().getUserId());

    }

    @Test
    void getCustomLobbyByLobbyId() {
        when(customLobbyRepository.findByLobbyId(anyString())).thenReturn(customLobbyEntity);

        CustomLobbyDto returnedValue = customLobbyService.getCustomLobbyByLobbyId("ASjdbajh5a");

        assertNotNull(returnedValue);
        assertNotNull(returnedValue.getAuthorDetails());
        assertNotNull(returnedValue.getQuestions());
        assertEquals(10, returnedValue.getQuestions().size());
        assertEquals(customLobbyId, returnedValue.getLobbyId());
        assertNotNull(returnedValue.getAuthorDetails());
        assertEquals(publicUserId, returnedValue.getAuthorDetails().getUserId());

    }

    @Test
    void getCustomLobbyByLobbyId_CustomLobbyServiceException(){
        when(customLobbyRepository.findByLobbyId(anyString())).thenReturn(null);

        assertThrows(CustomLobbyServiceException.class, () -> customLobbyService.getCustomLobbyByLobbyId("AKSJD5aJKA"));

    }

    private CustomLobbyDto getCustomLobbyDto(){
        CustomLobbyDto customLobbyDto = new CustomLobbyDto();
        customLobbyDto.setAuthorDetails(getAuthorDto());
        customLobbyDto.setLobbyId(customLobbyId);
        customLobbyDto.setQuestions(getQuestionsDto());

        return customLobbyDto;
    }

    private UserDto getAuthorDto(){
        UserDto userDto = new UserDto();
        userDto.setFirstName("Test");
        userDto.setLastName("Test");
        userDto.setClassDetails(new ClassDto());
        userDto.setUserId(publicUserId);

        return userDto;
    }

    private List<QuestionDto> getQuestionsDto(){
        List<QuestionDto> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(getOneQuestionDto());
        }

        return list;
    }

    private QuestionDto getOneQuestionDto(){
        QuestionDto questionDto = new QuestionDto();
        questionDto.setContent("Test content");
        questionDto.setCorrectAnswer("Correct answer");

        return questionDto;
    }

    private CustomLobbyEntity getCustomLobbyEntity(){
        CustomLobbyEntity customLobbyEntity = new CustomLobbyEntity();
        customLobbyEntity.setAuthorDetails(getAuthorEntity());
        customLobbyEntity.setLobbyId(customLobbyId);
        customLobbyEntity.setQuestions(getQuestionsEntity());

        return customLobbyEntity;
    }

    private UserEntity getAuthorEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Test");
        userEntity.setLastName("Test");
        userEntity.setClassDetails(new ClassEntity());
        userEntity.setUserId(publicUserId);

        return userEntity;
    }

    private List<QuestionEntity> getQuestionsEntity(){
        List<QuestionEntity> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(getOneQuestionEntity());
        }

        return list;
    }

    private QuestionEntity getOneQuestionEntity(){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent("Test content");
        questionEntity.setCorrectAnswer("Correct answer");

        return questionEntity;
    }
}