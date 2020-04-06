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
    StageSummaryReportDto getStageSummaryReportByUserId(String userId, int stageNumber);
    List<StageSummaryReportDto> getAllStagesReportsByUserId(String userId);
    ClassStageSummaryDto getClassStageSummaryByClassName(String className, int stageNumber);
    List<ClassStageSummaryDto> getAllClassStageSummaryByClassName(String className);
}
