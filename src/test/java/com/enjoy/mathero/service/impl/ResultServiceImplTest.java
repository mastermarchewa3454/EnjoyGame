package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.exceptions.ClassServiceException;
import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.*;
import com.enjoy.mathero.io.repository.ClassRepository;
import com.enjoy.mathero.io.repository.DuoResultRepository;
import com.enjoy.mathero.io.repository.SoloResultRepository;
import com.enjoy.mathero.io.repository.UserRepository;
import com.enjoy.mathero.shared.Utils;
import com.enjoy.mathero.shared.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

class ResultServiceImplTest {

    @Mock
    SoloResultRepository soloResultRepository;

    @Mock
    DuoResultRepository duoResultRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ClassRepository classRepository;

    @Mock
    Utils utils;

    @InjectMocks
    ResultServiceImpl resultService;

    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    SoloResultDto soloResultDto;
    SoloResultEntity soloResultEntity;
    DuoResultDto duoResultDto;
    DuoResultEntity duoResultEntity;
    List<SoloResultEntity> soloResultEntityList;
    List<DuoResultEntity> duoResultEntityList;
    UserEntity userEntity;
    String soloResultId = "AJb4k5kjabsda5";
    String publicUserId = "AAHSGDj5hjAG4ja5h";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        soloResultDto = getSoloDto();
        soloResultEntity = getSoloEntity();
        duoResultDto = getDuoDto();
        duoResultEntity = getDuoEntity();
        soloResultEntityList = getListSoloEntity();
        duoResultEntityList = getListDuoEntity();
        userEntity = getUserEntity();
    }

    @Test
    void createSoloResult() {
        when(utils.generateSoloResultID(anyInt())).thenReturn(soloResultId);
        when(soloResultRepository.save(any(SoloResultEntity.class))).thenReturn(soloResultEntity);

        SoloResultDto storedDetails = resultService.createSoloResult(soloResultDto);

        assertNotNull(storedDetails);
        assertNotNull(storedDetails.getUserDetails());
        assertEquals(soloResultDto.getEasyCorrect(), storedDetails.getEasyCorrect());
        assertEquals(soloResultDto.getEasyTotal(), storedDetails.getEasyTotal());
        assertEquals(soloResultDto.getMediumCorrect(), storedDetails.getMediumCorrect());
        assertEquals(soloResultDto.getMediumTotal(), storedDetails.getMediumTotal());
        assertEquals(soloResultDto.getHardCorrect(), storedDetails.getHardCorrect());
        assertEquals(soloResultDto.getHardTotal(), storedDetails.getHardTotal());
        assertEquals(soloResultDto.getScore(), storedDetails.getScore());
        assertEquals(soloResultDto.getStageNumber(), storedDetails.getStageNumber());
        assertEquals(soloResultId, storedDetails.getResultId());
    }

    @Test
    void createDuoResult() {
        when(utils.generateSoloResultID(anyInt())).thenReturn(soloResultId);
        when(duoResultRepository.save(any(DuoResultEntity.class))).thenReturn(duoResultEntity);

        DuoResultDto storedDetails = resultService.createDuoResult(duoResultDto);

        assertNotNull(storedDetails);
        assertNotNull(storedDetails.getUserDetails1());
        assertNotNull(storedDetails.getUserDetails2());
        assertEquals(duoResultDto.getScore(), storedDetails.getScore());
        assertEquals(duoResultDto.getStageNumber(), storedDetails.getStageNumber());
        assertEquals(soloResultId, storedDetails.getResultId());
    }

    @Test
    void getSoloResultsByUserId() {
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(soloResultRepository.findAllByUserDetails(any(UserEntity.class))).thenReturn(soloResultEntityList);

        List<SoloResultDto> returnedValue = resultService.getSoloResultsByUserId("Ahfjb5JKB");

        assertNotNull(returnedValue);
        assertEquals(soloResultEntityList.size(), returnedValue.size());
        for(int i=0;i<returnedValue.size();i++){
            assertNotNull(returnedValue.get(i).getUserDetails());
            assertEquals(soloResultId, returnedValue.get(i).getResultId());
        }

    }

    @Test
    void getSoloResultsByUserId_UserServiceException(){
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, () -> resultService.getSoloResultsByUserId("AJsdbjh53"));

    }

    @Test
    void getTop10() {
        when(soloResultRepository.findTop10ByOrderByScoreDesc()).thenReturn(soloResultEntityList);

        List<SoloResultDto> returnedValue = resultService.getTop10();

        assertNotNull(returnedValue);
        assertEquals(soloResultEntityList.size(), returnedValue.size());

    }

    @Test
    void getTop10_stageNumberSpecified() {
        when(soloResultRepository.findTop10ByStageNumberOrderByScoreDesc(anyInt())).thenReturn(soloResultEntityList);

        List<SoloResultDto> returnedValue = resultService.getTop10(3);

        assertNotNull(returnedValue);
        assertEquals(soloResultEntityList.size(), returnedValue.size());
    }

    @Test
    void getTop10Duo_stageNumberSpecified() {
        when(duoResultRepository.findTop10ByStageNumberOrderByScoreDesc(anyInt())).thenReturn(duoResultEntityList);

        List<DuoResultDto> returnedValue = resultService.getTop10Duo(3);

        assertNotNull(returnedValue);
        assertEquals(duoResultEntityList.size(), returnedValue.size());
    }

    @Test
    void getTop10Duo() {
        when(duoResultRepository.findTop10ByOrderByScoreDesc()).thenReturn(duoResultEntityList);

        List<DuoResultDto> returnedValue = resultService.getTop10Duo();

        assertNotNull(returnedValue);
        assertEquals(duoResultEntityList.size(), returnedValue.size());
    }

    @Test
    void getStageSummaryReportByUserId() {
        StageSummaryReportEntity stageSummaryReportEntity = getStageSummaryEntity();

        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(soloResultRepository.getStageSummaryReport(anyString(), anyInt())).thenReturn(stageSummaryReportEntity);

        StageSummaryReportDto returnedValue = resultService.getStageSummaryReportByUserId("ASJDH4jha", 3);

        assertNotNull(returnedValue);
        assertEquals(stageSummaryReportEntity.getEasyCorrect(), returnedValue.getEasyCorrect());
        assertEquals(stageSummaryReportEntity.getEasyTotal(), returnedValue.getEasyTotal());
        assertEquals(stageSummaryReportEntity.getHardCorrect(), returnedValue.getHardCorrect());
        assertEquals(stageSummaryReportEntity.getHardTotal(), returnedValue.getHardTotal());
        assertEquals(stageSummaryReportEntity.getMediumCorrect(), returnedValue.getMediumCorrect());
        assertEquals(stageSummaryReportEntity.getMediumTotal(), returnedValue.getMediumTotal());
        assertEquals(stageSummaryReportEntity.getStageNumber(), returnedValue.getStageNumber());

    }

    @Test
    void getStageSummaryReportByUserId_UserServiceException() {
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, () -> resultService.getStageSummaryReportByUserId("AShdjg4ahjbd", 2));

    }

    @Test
    void getAllStagesReportsByUserId() {
        List<StageSummaryReportEntity> stageSummaryReportEntityList = getListStageSummaryEntity();

        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(soloResultRepository.getAllStageSummaryReports(anyString())).thenReturn(stageSummaryReportEntityList);

        List<StageSummaryReportDto> returnedValue = resultService.getAllStagesReportsByUserId("asjdhga44HAd");

        assertNotNull(returnedValue);
        assertEquals(stageSummaryReportEntityList.size(), returnedValue.size());

    }

    @Test
    void getAllStagesReportsByUserId_UserServiceException(){
        when(userRepository.findByUserId(anyString())).thenReturn(null);

        assertThrows(UserServiceException.class, () -> resultService.getAllStagesReportsByUserId("ASJDGKJAH5basd"));

    }

    @Test
    void getClassStageSummaryByClassId() {
        ClassStageSummaryEntity classStageSummaryEntity = getClassStageSummaryEntity();

        when(soloResultRepository.getClassStageSummary(anyString(), anyInt())).thenReturn(classStageSummaryEntity);
        when(classRepository.findByClassId(anyString())).thenReturn(new ClassEntity());

        ClassStageSummaryDto returnedValue = resultService.getClassStageSummaryByClassId("ASdjhj5ABS", 2);

        assertNotNull(returnedValue);
        assertEquals(classStageSummaryEntity.getClassId(), returnedValue.getClassId());
        assertEquals(classStageSummaryEntity.getClassName(), returnedValue.getClassName());
        assertEquals(classStageSummaryEntity.getEasyCorrect(), returnedValue.getEasyCorrect());
        assertEquals(classStageSummaryEntity.getEasyTotal(), returnedValue.getEasyTotal());
        assertEquals(classStageSummaryEntity.getHardCorrect(), returnedValue.getHardCorrect());
        assertEquals(classStageSummaryEntity.getHardTotal(), returnedValue.getHardTotal());
        assertEquals(classStageSummaryEntity.getMediumCorrect(), returnedValue.getMediumCorrect());
        assertEquals(classStageSummaryEntity.getMediumTotal(), returnedValue.getMediumTotal());
        assertEquals(classStageSummaryEntity.getStageNumber(), returnedValue.getStageNumber());

    }

    @Test
    void getClassStageSummaryByClassId_ClassServiceException() {
        when(classRepository.findByClassId(anyString())).thenReturn(null);

        assertThrows(ClassServiceException.class, () -> resultService.getClassStageSummaryByClassId("ASJdh5ajsbd", 1));

    }

    @Test
    void getAllClassStageSummaryByClassId() {
        List<ClassStageSummaryEntity> classStageSummaryEntityList = getListClassStageSummary();

        when(classRepository.findByClassId(anyString())).thenReturn(new ClassEntity());
        when(soloResultRepository.getAllClassStageSummary(anyString())).thenReturn(classStageSummaryEntityList);

        List<ClassStageSummaryDto> returnedValue = resultService.getAllClassStageSummaryByClassId("ASJd5abjsd");

        assertNotNull(returnedValue);
        assertEquals(classStageSummaryEntityList.size(), returnedValue.size());

    }

    @Test
    void getAllClassStageSummaryByClassId_ClassServiceException() {
        when(classRepository.findByClassId(anyString())).thenReturn(null);

        assertThrows(ClassServiceException.class, () -> resultService.getAllClassStageSummaryByClassId("AJKSdg5ajkbsd"));

    }

    private SoloResultDto getSoloDto(){
        SoloResultDto soloResultDto = new SoloResultDto();
        soloResultDto.setUserDetails(new UserDto());
        soloResultDto.setEasyCorrect(5);
        soloResultDto.setEasyTotal(10);
        soloResultDto.setMediumCorrect(7);
        soloResultDto.setMediumTotal(15);
        soloResultDto.setHardCorrect(3);
        soloResultDto.setHardTotal(7);
        soloResultDto.setScore(300);
        soloResultDto.setStageNumber(4);

        return soloResultDto;
    }

    private SoloResultEntity getSoloEntity(){
        SoloResultEntity soloResultEntity = new SoloResultEntity();
        soloResultEntity.setUserDetails(new UserEntity());
        soloResultEntity.setResultId(soloResultId);
        soloResultEntity.setEasyCorrect(5);
        soloResultEntity.setEasyTotal(10);
        soloResultEntity.setMediumCorrect(7);
        soloResultEntity.setMediumTotal(15);
        soloResultEntity.setHardCorrect(3);
        soloResultEntity.setHardTotal(7);
        soloResultEntity.setScore(300);
        soloResultEntity.setStageNumber(4);

        return soloResultEntity;
    }

    private DuoResultDto getDuoDto(){
        DuoResultDto duoResultDto = new DuoResultDto();
        duoResultDto.setUserDetails1(new UserDto());
        duoResultDto.setUserDetails2(new UserDto());
        duoResultDto.setScore(300);
        duoResultDto.setStageNumber(5);

        return duoResultDto;
    }

    private DuoResultEntity getDuoEntity(){
        DuoResultEntity duoResultEntity = new DuoResultEntity();
        duoResultEntity.setUserDetails1(new UserEntity());
        duoResultEntity.setUserDetails2(new UserEntity());
        duoResultEntity.setResultId(soloResultId);
        duoResultEntity.setScore(300);
        duoResultEntity.setStageNumber(5);

        return duoResultEntity;
    }

    private List<SoloResultEntity> getListSoloEntity(){
        List<SoloResultEntity> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(soloResultEntity);
        }

        return list;
    }

    private List<DuoResultEntity> getListDuoEntity(){
        List<DuoResultEntity> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(duoResultEntity);
        }

        return list;
    }

    private UserEntity getUserEntity(){
        UserEntity studentEntity = new UserEntity();
        studentEntity.setUserId(publicUserId);
        studentEntity.setFirstName("Test");
        studentEntity.setLastName("Test");
        studentEntity.setUsername("student");
        studentEntity.setMaxStageCanPlay(0);
        studentEntity.setEmail("test@test.com");
        return studentEntity;
    }

    private StageSummaryReportEntity getStageSummaryEntity(){
        StageSummaryReportEntity stageSummaryReportEntity = factory.createProjection(StageSummaryReportEntity.class);
        stageSummaryReportEntity.setEasyCorrect(7);
        stageSummaryReportEntity.setEasyTotal(10);
        stageSummaryReportEntity.setMediumCorrect(4);
        stageSummaryReportEntity.setMediumTotal(8);
        stageSummaryReportEntity.setHardCorrect(10);
        stageSummaryReportEntity.setHardTotal(12);
        stageSummaryReportEntity.setUserId(publicUserId);
        stageSummaryReportEntity.setStageNumber(2);

        return stageSummaryReportEntity;
    }

    private List<StageSummaryReportEntity> getListStageSummaryEntity(){
        List<StageSummaryReportEntity> list = new ArrayList<>();
        for(int i = 0;i<5;i++){
            list.add(getStageSummaryEntity());
        }
        return list;
    }

    private ClassStageSummaryEntity getClassStageSummaryEntity(){
        ClassStageSummaryEntity classStageSummaryEntity = factory.createProjection(ClassStageSummaryEntity.class);
        classStageSummaryEntity.setClassId("ASHJDAGJA5ajhvs");
        classStageSummaryEntity.setClassName("Class A");
        classStageSummaryEntity.setEasyCorrect(4);
        classStageSummaryEntity.setEasyTotal(6);
        classStageSummaryEntity.setMediumCorrect(10);
        classStageSummaryEntity.setMediumTotal(12);
        classStageSummaryEntity.setHardCorrect(8);
        classStageSummaryEntity.setHardTotal(10);
        classStageSummaryEntity.setStageNumber(1);

        return classStageSummaryEntity;
    }

    private List<ClassStageSummaryEntity> getListClassStageSummary(){
        List<ClassStageSummaryEntity> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(getClassStageSummaryEntity());
        }

        return list;
    }
}