package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.SoloResultDto;
import com.enjoy.mathero.shared.dto.StageSummaryReportDto;

import java.util.List;

public interface SoloResultService {
    SoloResultDto createSoloResult(SoloResultDto soloResultDto);
    List<SoloResultDto> getSoloResultsByUserId(String userId);
    List<SoloResultDto> getTop10();
    StageSummaryReportDto getStageSummaryReportByUserId(String userId, int stageNumber);
    List<StageSummaryReportDto> getAllStagesReportsByUserId(String userId);
}
