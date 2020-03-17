package com.enjoy.mathero.io.entity;

public interface StageSummaryReportEntity {
    String getUserId();
    Integer getStageNumber();
    Integer getEasyCorrect();
    Integer getMediumCorrect();
    Integer getHardCorrect();
    Integer getEasyTotal();
    Integer getMediumTotal();
    Integer getHardTotal();
}
