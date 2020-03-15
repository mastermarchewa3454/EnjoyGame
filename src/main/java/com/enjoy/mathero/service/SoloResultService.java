package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.SoloResultDto;

import java.util.List;

public interface SoloResultService {
    SoloResultDto createSoloResult(SoloResultDto soloResultDto);
    List<SoloResultDto> getSoloResultsByUserId(String userId);
}
