package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.ClassServiceException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.*;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.DuoResultRepository;
import com.enjoy.mathero.io.repository.SoloResultRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.*;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ResultService interface. Business logic to deal with results.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    SoloResultRepository soloResultRepository;

    @Autowired
    DuoResultRepository duoResultRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    Utils utils;

    /**
     * Creates solo result and stores it in the database
     * @param soloResultDto details to be stored
     * @return details saved
     */
    @Override
    public SoloResultDto createSoloResult(SoloResultDto soloResultDto) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setPropertyCondition(new Condition<Object, Object>() {
            public boolean applies(MappingContext<Object, Object> context) {
                return !(context.getSource() instanceof PersistentCollection);
            }
        });


        SoloResultEntity soloResultEntity = modelMapper.map(soloResultDto, SoloResultEntity.class);

        String publicResultId = utils.generateSoloResultID(30);
        soloResultEntity.setResultId(publicResultId);

        SoloResultEntity saveResult = soloResultRepository.save(soloResultEntity);

        SoloResultDto returnValue = modelMapper.map(saveResult, SoloResultDto.class);

        return returnValue;
    }

    /**
     * Creates duo result and stores it in the database
     * @param duoResultDto details to be saved
     * @return stored details
     */
    @Override
    public DuoResultDto createDuoResult(DuoResultDto duoResultDto) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setPropertyCondition(new Condition<Object, Object>() {
            public boolean applies(MappingContext<Object, Object> context) {
                return !(context.getSource() instanceof PersistentCollection);
            }
        });


        DuoResultEntity duoResultEntity = modelMapper.map(duoResultDto, DuoResultEntity.class);

        String publicResultId = utils.generateSoloResultID(30);
        duoResultEntity.setResultId(publicResultId);

        DuoResultEntity savedResult = duoResultRepository.save(duoResultEntity);

        DuoResultDto returnValue = modelMapper.map(savedResult, DuoResultDto.class);

        return returnValue;
    }

    /**
     * Returns list of solo results with give userId
     * @param userId id of the user
     * @return list of solo results
     */
    @Override
    public List<SoloResultDto> getSoloResultsByUserId(String userId) {
        List<SoloResultDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<SoloResultEntity> soloResultEntities = soloResultRepository.findAllByUserDetails(userEntity);
        for(SoloResultEntity soloResultEntity: soloResultEntities){
            SoloResultDto soloResultDto = new SoloResultDto();
            UserDto userDto = new UserDto();
            userDto.setUserId(soloResultEntity.getUserDetails().getUserId());
            userDto.setUsername(soloResultEntity.getUserDetails().getUsername());
            soloResultDto.setScore(soloResultEntity.getScore());
            soloResultDto.setResultId(soloResultEntity.getResultId());
            soloResultDto.setEasyCorrect(soloResultEntity.getEasyCorrect());
            soloResultDto.setEasyTotal(soloResultEntity.getEasyTotal());
            soloResultDto.setMediumTotal(soloResultEntity.getMediumTotal());
            soloResultDto.setMediumCorrect(soloResultEntity.getMediumCorrect());
            soloResultDto.setHardCorrect(soloResultEntity.getHardCorrect());
            soloResultDto.setHardTotal(soloResultEntity.getHardTotal());
            soloResultDto.setStageNumber(soloResultEntity.getStageNumber());
            soloResultDto.setUserDetails(userDto);

            returnValue.add(soloResultDto);
        }


        return returnValue;
    }

    /**
     * Returns 20 solo results with highest score value, sorted.
     * @return list of sorted solo results
     */
    @Override
    public List<SoloResultDto> getTop20() {
        List<SoloResultDto> returnValue = new ArrayList<>();

        List<SoloResultEntity> top20 = soloResultRepository.findTop20ByOrderByScoreDesc();

        for(SoloResultEntity soloResultEntity: top20){
            SoloResultDto soloResultDto = new SoloResultDto();
            UserDto userDto = new UserDto();
            userDto.setUserId(soloResultEntity.getUserDetails().getUserId());
            userDto.setUsername(soloResultEntity.getUserDetails().getUsername());
            soloResultDto.setScore(soloResultEntity.getScore());
            soloResultDto.setResultId(soloResultEntity.getResultId());
            soloResultDto.setEasyCorrect(soloResultEntity.getEasyCorrect());
            soloResultDto.setEasyTotal(soloResultEntity.getEasyTotal());
            soloResultDto.setMediumTotal(soloResultEntity.getMediumTotal());
            soloResultDto.setMediumCorrect(soloResultEntity.getMediumCorrect());
            soloResultDto.setHardCorrect(soloResultEntity.getHardCorrect());
            soloResultDto.setHardTotal(soloResultEntity.getHardTotal());
            soloResultDto.setStageNumber(soloResultEntity.getStageNumber());
            soloResultDto.setUserDetails(userDto);

            returnValue.add(soloResultDto);        }

        return returnValue;
    }

    /**
     * Returns 20 solo results with highest score value and specified stage number, sorted.
     * @param stageNumber number of stage
     * @return list of solo results
     */
    @Override
    public List<SoloResultDto> getTop20(int stageNumber) {
        List<SoloResultDto> returnValue = new ArrayList<>();

        List<SoloResultEntity> top20 = soloResultRepository.findTop20ByStageNumberOrderByScoreDesc(stageNumber);
        ModelMapper modelMapper = new ModelMapper();

        for(SoloResultEntity soloResultEntity: top20){
            SoloResultDto soloResultDto = new SoloResultDto();
            UserDto userDto = new UserDto();
            userDto.setUserId(soloResultEntity.getUserDetails().getUserId());
            userDto.setUsername(soloResultEntity.getUserDetails().getUsername());
            soloResultDto.setScore(soloResultEntity.getScore());
            soloResultDto.setResultId(soloResultEntity.getResultId());
            soloResultDto.setEasyCorrect(soloResultEntity.getEasyCorrect());
            soloResultDto.setEasyTotal(soloResultEntity.getEasyTotal());
            soloResultDto.setMediumTotal(soloResultEntity.getMediumTotal());
            soloResultDto.setMediumCorrect(soloResultEntity.getMediumCorrect());
            soloResultDto.setHardCorrect(soloResultEntity.getHardCorrect());
            soloResultDto.setHardTotal(soloResultEntity.getHardTotal());
            soloResultDto.setStageNumber(soloResultEntity.getStageNumber());
            soloResultDto.setUserDetails(userDto);

            returnValue.add(soloResultDto);        }

        return returnValue;
    }

    /**
     * Returns 20 duo results with highest score value, sorted.
     * @return list of duo results
     */
    @Override
    public List<DuoResultDto> getTop20Duo() {
        List<DuoResultDto> returnValue = new ArrayList<>();

        List<DuoResultEntity> top20 = duoResultRepository.findTop20ByOrderByScoreDesc();
        ModelMapper modelMapper = new ModelMapper();

        for(DuoResultEntity duoResultEntity: top20){
            DuoResultDto duoResultDto = new DuoResultDto();
            UserDto userDto1 = new UserDto();
            userDto1.setUserId(duoResultEntity.getUserDetails1().getUserId());
            userDto1.setUsername(duoResultEntity.getUserDetails1().getUsername());
            UserDto userDto2 = new UserDto();
            userDto2.setUserId(duoResultEntity.getUserDetails1().getUserId());
            userDto2.setUsername(duoResultEntity.getUserDetails1().getUsername());
            duoResultDto.setScore(duoResultEntity.getScore());
            duoResultDto.setResultId(duoResultEntity.getResultId());
            duoResultDto.setStageNumber(duoResultEntity.getStageNumber());
            duoResultDto.setUserDetails1(userDto1);
            duoResultDto.setUserDetails2(userDto2);

            returnValue.add(duoResultDto);
        }

        return returnValue;
    }

    /**
     * Returns 20 duo results with highest score value and specified stage number, sorted.
     * @param stageNumber number of stage
     * @return list of duo results
     */
    @Override
    public List<DuoResultDto> getTop20Duo(int stageNumber) {
        List<DuoResultDto> returnValue = new ArrayList<>();

        List<DuoResultEntity> top20 = duoResultRepository.findTop20ByStageNumberOrderByScoreDesc(stageNumber);
        ModelMapper modelMapper = new ModelMapper();

        for(DuoResultEntity duoResultEntity: top20){
            DuoResultDto duoResultDto = new DuoResultDto();
            UserDto userDto1 = new UserDto();
            userDto1.setUserId(duoResultEntity.getUserDetails1().getUserId());
            userDto1.setUsername(duoResultEntity.getUserDetails1().getUsername());
            UserDto userDto2 = new UserDto();
            userDto2.setUserId(duoResultEntity.getUserDetails1().getUserId());
            userDto2.setUsername(duoResultEntity.getUserDetails1().getUsername());
            duoResultDto.setScore(duoResultEntity.getScore());
            duoResultDto.setResultId(duoResultEntity.getResultId());
            duoResultDto.setStageNumber(duoResultEntity.getStageNumber());
            duoResultDto.setUserDetails1(userDto1);
            duoResultDto.setUserDetails2(userDto2);

            returnValue.add(duoResultDto);
        }

        return returnValue;
    }

    /**
     * Returns stage summary report
     * @param userId id of the user
     * @param stageNumber number of stage
     * @return details of stage summary report
     */
    @Override
    public StageSummaryReportDto getStageSummaryReportByUserId(String userId, int stageNumber) {
        StageSummaryReportDto returnValue = new StageSummaryReportDto();

        if(userRepository.findByUserId(userId) == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        StageSummaryReportEntity results = soloResultRepository.getStageSummaryReport(userId, stageNumber);
        returnValue.setUserId(results.getUserId());
        returnValue.setUsername(results.getUsername());
        returnValue.setStageNumber(results.getStageNumber());
        returnValue.setEasyCorrect(results.getEasyCorrect());
        returnValue.setMediumCorrect(results.getMediumCorrect());
        returnValue.setHardCorrect(results.getHardCorrect());
        returnValue.setEasyTotal(results.getEasyTotal());
        returnValue.setMediumTotal(results.getMediumTotal());
        returnValue.setHardTotal(results.getHardTotal());

        return returnValue;
    }

    /**
     * Returns list of all stage summary reports
     * @param userId id of user
     * @return list of stage summary reports
     */
    @Override
    public List<StageSummaryReportDto> getAllStagesReportsByUserId(String userId) {
        List<StageSummaryReportDto> returnValue = new ArrayList<>();

        if(userRepository.findByUserId(userId) == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<StageSummaryReportEntity> results = soloResultRepository.getAllStageSummaryReports(userId);
        for(StageSummaryReportEntity entity: results){
            StageSummaryReportDto stageSummaryReportDto = new StageSummaryReportDto();
            BeanUtils.copyProperties(entity, stageSummaryReportDto);
            returnValue.add(stageSummaryReportDto);
        }

        return returnValue;
    }

    /**
     * Returns class stage summary report
     * @param classId id of the class
     * @param stageNumber number of stage
     * @return list of class stage summaries
     */
    @Override
    public ClassStageSummaryDto getClassStageSummaryByClassId(String classId, int stageNumber) {
        ClassStageSummaryDto returnValue = new ClassStageSummaryDto();

        if(classRepository.findByClassId(classId) == null)
            throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        ClassStageSummaryEntity results = soloResultRepository.getClassStageSummary(classId, stageNumber);
        returnValue.setClassId(results.getClassId());
        returnValue.setClassName(results.getClassName());
        returnValue.setStageNumber(results.getStageNumber());
        returnValue.setEasyCorrect(results.getEasyCorrect());
        returnValue.setMediumCorrect(results.getMediumCorrect());
        returnValue.setHardCorrect(results.getHardCorrect());
        returnValue.setEasyTotal(results.getEasyTotal());
        returnValue.setMediumTotal(results.getMediumTotal());
        returnValue.setHardTotal(results.getHardTotal());

        return returnValue;
    }

    /**
     * Returns list of all class stage summaries
     * @param classId id of the class
     * @return list of class stage summaries
     */
    @Override
    public List<ClassStageSummaryDto> getAllClassStageSummaryByClassId(String classId) {
        List<ClassStageSummaryDto> returnValue = new ArrayList<>();

        if(classRepository.findByClassId(classId) == null)
            throw new ClassServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<ClassStageSummaryEntity> results = soloResultRepository.getAllClassStageSummary(classId);
        for(ClassStageSummaryEntity entity: results){
            ClassStageSummaryDto stageSummaryReportDto = new ClassStageSummaryDto();
            BeanUtils.copyProperties(entity, stageSummaryReportDto);
            returnValue.add(stageSummaryReportDto);
        }

        return returnValue;
    }
}
