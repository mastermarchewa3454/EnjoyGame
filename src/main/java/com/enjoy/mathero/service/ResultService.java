package com.enjoy.mathero.service;

import com.enjoy.mathero.io.entity.ClassStageSummaryEntity;
import com.enjoy.mathero.shared.dto.ClassStageSummaryDto;
import com.enjoy.mathero.shared.dto.DuoResultDto;
import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.StageSummaryReportDto;

import java.util.List;

/**
 * Business logic to deal with results.
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public interface ResultService {
    SoloResultDto createSoloResult(SoloResultDto soloResultDto);
    DuoResultDto createDuoResult(DuoResultDto duoResultDto);
    List<SoloResultDto> getSoloResultsByUserId(String userId);
    List<SoloResultDto> getTop20();
    List<SoloResultDto> getTop20(int stageNumber);
    List<DuoResultDto> getTop20Duo();
    List<DuoResultDto> getTop20Duo(int stageNumber);
    StageSummaryReportDto getStageSummaryReportByUserId(String userId, int stageNumber);
    List<StageSummaryReportDto> getAllStagesReportsByUserId(String userId);
    ClassStageSummaryDto getClassStageSummaryByClassId(String classId, int stageNumber);
    List<ClassStageSummaryDto> getAllClassStageSummaryByClassId(String classId);
}
