package com.enjoy.mathero.service;

import com.enjoy.mathero.io.entity.ClassStageSummaryEntity;
import com.enjoy.mathero.shared.dto.ClassStageSummaryDto;
import com.enjoy.mathero.shared.dto.DuoResultDto;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.StageSummaryReportDto;

import java.util.List;

public interface ResultService {
    SoloResultDto createSoloResult(SoloResultDto soloResultDto);
    DuoResultDto createDuoResult(DuoResultDto duoResultDto);
    List<SoloResultDto> getSoloResultsByUserId(String userId);
    List<SoloResultDto> getTop10();
    List<SoloResultDto> getTop10(int stageNumber);
    List<DuoResultDto> getTop10Duo();
    List<DuoResultDto> getTop10Duo(int stageNumber);
    StageSummaryReportDto getStageSummaryReportByUserId(String userId, int stageNumber);
    List<StageSummaryReportDto> getAllStagesReportsByUserId(String userId);
    ClassStageSummaryDto getClassStageSummaryByClassId(String classId, int stageNumber);
    List<ClassStageSummaryDto> getAllClassStageSummaryByClassId(String classId);
}