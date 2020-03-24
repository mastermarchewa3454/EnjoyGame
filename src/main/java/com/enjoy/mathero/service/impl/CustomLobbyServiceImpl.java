package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.CustomLobbyEntity;
import com.enjoy.mathero.io.entity.QuestionEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.CustomLobbyRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.service.CustomLobbyService;
import com.enjoy.mathero.shared.MapUtils;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.CustomLobbyDto;
import com.enjoy.mathero.shared.dto.QuestionDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomLobbyServiceImpl implements CustomLobbyService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomLobbyRepository customLobbyRepository;

    @Autowired
    Utils utils;


    @Override
    public CustomLobbyDto createCustomLobby(CustomLobbyDto customLobbyDto) {

        CustomLobbyEntity toSave = new CustomLobbyEntity();
        BeanUtils.copyProperties(customLobbyDto, toSave);

        UserEntity authorEntity = new UserEntity();
        BeanUtils.copyProperties(customLobbyDto.getAuthorDetails(), authorEntity);

        List<QuestionEntity> questionEntities = new ArrayList<>();
        List<QuestionDto> questionDtos = customLobbyDto.getQuestions();
        for(int i=0;i<questionDtos.size();i++){
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(questionDtos.get(i),questionEntity);
            questionEntity.setLobbyDetails(toSave);
            questionEntities.add(questionEntity);
        }

        toSave.setAuthorDetails(authorEntity);
        toSave.setQuestions(questionEntities);
        toSave.setLobbyId(utils.generateCustomLobbyId(30));

        CustomLobbyEntity saved = customLobbyRepository.save(toSave);

        CustomLobbyDto returnValue = MapUtils.customLobbyEntityToCustomLobbyDto(saved);

        return returnValue;
    }

    @Override
    public CustomLobbyDto getCustomLobbyByLobbyId(String lobbyId) {

        CustomLobbyEntity customLobbyEntity = customLobbyRepository.findByLobbyId(lobbyId);

        if(customLobbyEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        CustomLobbyDto returnValue = MapUtils.customLobbyEntityToCustomLobbyDto(customLobbyEntity);

        return returnValue;
    }
}
