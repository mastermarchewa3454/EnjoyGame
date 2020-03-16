package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.SoloResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import com.enjoy.mathero.ui.model.response.SoloResultRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SoloResultController {

    @Autowired
    UserService userService;

    @Autowired
    SoloResultService resultService;

    @GetMapping(path = "/results/{resultId}")
    public SoloResultRest getResult(@PathVariable String resultId){
        return null;
    }

    /*@GetMapping(path = "/users/{userId}/results")
    public List<SoloResultRest> getResultsByUserId(@PathVariable String userId){
        return null;
    }*/

    @GetMapping(path = "/results/top10")
    public List<SoloResultRest> getTop10(){
        List<SoloResultRest> returnValue = new ArrayList<>();

        List<SoloResultDto> soloResultDtos = resultService.getTop10();
        for(SoloResultDto soloResultDto: soloResultDtos){
            SoloResultRest soloResultRest = new SoloResultRest();
            BeanUtils.copyProperties(soloResultDto, soloResultRest);
            soloResultRest.setUserId(soloResultDto.getUserDetails().getUserId());
            returnValue.add(soloResultRest);
        }
        return returnValue;
    }

    @GetMapping(path = "/results")
    public List<SoloResultRest> getAllResults(){
        return null;
    }

    @PostMapping(path = "/users/{userId}/results")
    public SoloResultRest createResult(@PathVariable String userId, @RequestBody SoloResultRequestModel resultDetails){
        SoloResultRest returnValue = new SoloResultRest();

        UserDto userDto = userService.getUserByUserId(userId);
        if(userDto == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        SoloResultDto soloResultDto = new SoloResultDto();
        soloResultDto.setUserDetails(userDto);
        BeanUtils.copyProperties(resultDetails, soloResultDto);

        SoloResultDto createdResult = resultService.createSoloResult(soloResultDto);
        BeanUtils.copyProperties(createdResult, returnValue);
        returnValue.setUserId(userDto.getUserId());

        return returnValue;
    }
}
