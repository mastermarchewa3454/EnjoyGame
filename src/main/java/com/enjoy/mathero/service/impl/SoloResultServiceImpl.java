package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.SoloResultEntity;
import com.enjoy.mathero.io.entity.StageSummaryReportEntity;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.io.repository.SoloResultRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.service.SoloResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.StageSummaryReportDto;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@Service
public class SoloResultServiceImpl implements SoloResultService {

    @Autowired
    SoloResultRepository soloResultRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public SoloResultDto createSoloResult(SoloResultDto soloResultDto) {

        ModelMapper modelMapper = new ModelMapper();
        SoloResultEntity soloResultEntity = modelMapper.map(soloResultDto, SoloResultEntity.class);

        String publicResultId = utils.generateSoloResultID(30);
        soloResultEntity.setResultId(publicResultId);

        SoloResultEntity saveResult = soloResultRepository.save(soloResultEntity);

        SoloResultDto returnValue = modelMapper.map(saveResult, SoloResultDto.class);

        return returnValue;
    }

    @Override
    public List<SoloResultDto> getSoloResultsByUserId(String userId) {
        List<SoloResultDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<SoloResultEntity> soloResultEntities = soloResultRepository.findAllByUserDetails(userEntity);
        ModelMapper modelMapper = new ModelMapper();
        for(SoloResultEntity soloResultEntity: soloResultEntities){
            returnValue.add(modelMapper.map(soloResultEntity, SoloResultDto.class));
        }

        return returnValue;
    }

    @Override
    public List<SoloResultDto> getTop10() {
        List<SoloResultDto> returnValue = new ArrayList<>();

        List<SoloResultEntity> top10 = soloResultRepository.findTop10ByOrderByScoreDesc();
        ModelMapper modelMapper = new ModelMapper();

        for(SoloResultEntity soloResultEntity: top10){
            returnValue.add(modelMapper.map(soloResultEntity, SoloResultDto.class));
        }

        return returnValue;
    }

    @Override
    public StageSummaryReportDto getStageSummaryReportByUserId(String userId, int stageNumber) {
        StageSummaryReportDto returnValue = new StageSummaryReportDto();

        StageSummaryReportEntity results = soloResultRepository.getStageSummaryReport(userId, stageNumber);
        returnValue.setUserId(results.getUserId());
        returnValue.setStageNumber(results.getStageNumber());
        returnValue.setEasyCorrect(results.getEasyCorrect());
        returnValue.setMediumCorrect(results.getMediumCorrect());
        returnValue.setHardCorrect(results.getHardCorrect());
        returnValue.setEasyTotal(results.getEasyTotal());
        returnValue.setMediumTotal(results.getMediumTotal());
        returnValue.setHardTotal(results.getHardTotal());

        return returnValue;
    }

    @Override
    public List<StageSummaryReportDto> getAllStagesReportsByUserId(String userId) {
        List<StageSummaryReportDto> returnValue = new ArrayList<>();

        List<StageSummaryReportEntity> results = soloResultRepository.getAllStageSummaryReports(userId);
        for(StageSummaryReportEntity entity: results){
            StageSummaryReportDto stageSummaryReportDto = new StageSummaryReportDto();
            BeanUtils.copyProperties(entity, stageSummaryReportDto);
            returnValue.add(stageSummaryReportDto);
        }

        return returnValue;
    }
}
