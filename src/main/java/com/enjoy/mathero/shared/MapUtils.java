package com.enjoy.mathero.shared;

import com.enjoy.mathero.io.entity.ClassEntity;
import com.enjoy.mathero.io.entity.CustomLobbyEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.CustomLobbyDto;
import com.enjoy.mathero.shared.dto.QuestionDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.response.ClassRest;
import com.enjoy.mathero.ui.model.response.CustomLobbyRest;
import com.enjoy.mathero.ui.model.response.QuestionRest;
import com.enjoy.mathero.ui.model.response.StudentRest;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with static functions to map between entity, dto and rest classes
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class MapUtils {
    public static ClassRest classDtoToClassRest(ClassDto classDto){
        ClassRest classRest = new ClassRest();
        classRest.setClassName(classDto.getClassName());
        classRest.setTeacherId(classDto.getTeacherDetails().getUserId());
        classRest.setTeacherFirstName(classDto.getTeacherDetails().getFirstName());
        classRest.setTeacherLastName(classDto.getTeacherDetails().getLastName());
        List<StudentRest> students = new ArrayList<>();
        List<UserDto> studentsDto = classDto.getStudents();
        for(int i =0;i<studentsDto.size();i++){
            StudentRest studentRest = new StudentRest();
            BeanUtils.copyProperties(studentsDto.get(i),studentRest);
            students.add(studentRest);
        }
        classRest.setStudents(students);

        return classRest;
    }

    public static ClassDto classEntityToClassDto(ClassEntity classEntity){
        ClassDto classDto = new ClassDto();
        UserDto teacher = new UserDto();
        BeanUtils.copyProperties(classEntity.getTeacherDetails(), teacher);
        List<UserDto> students = new ArrayList<>();
        for(UserEntity userEntity: classEntity.getStudents()){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            students.add(userDto);
        }
        classDto.setTeacherDetails(teacher);
        classDto.setStudents(students);
        BeanUtils.copyProperties(classEntity, classDto);

        return classDto;
    }

    public static CustomLobbyDto customLobbyEntityToCustomLobbyDto(CustomLobbyEntity saved){
        CustomLobbyDto returnValue = new CustomLobbyDto();
        BeanUtils.copyProperties(saved, returnValue);

        List<QuestionDto> savedQuestionDtos = new ArrayList<>();
        for(int i=0;i<saved.getQuestions().size();i++){
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(saved.getQuestions().get(i), questionDto);
            savedQuestionDtos.add(questionDto);
        }

        returnValue.setQuestions(savedQuestionDtos);

        UserDto authorDetails = new UserDto();
        BeanUtils.copyProperties(saved.getAuthorDetails(),authorDetails);

        returnValue.setAuthorDetails(authorDetails);

        return returnValue;
    }

    public static CustomLobbyRest customLobbyDtoToCustomLobbyRest(CustomLobbyDto saved){
        CustomLobbyRest returnValue = new CustomLobbyRest();
        returnValue.setAuthorId(saved.getAuthorDetails().getUserId());

        List<QuestionRest> questionRests = new ArrayList<>();
        for(int i=0;i<saved.getQuestions().size();i++){
            QuestionRest questionRest = new QuestionRest();
            BeanUtils.copyProperties(saved.getQuestions().get(i),questionRest);
            questionRests.add(questionRest);
        }

        returnValue.setQuestions(questionRests);
        returnValue.setLobbyId(saved.getLobbyId());

        return returnValue;
    }
}
