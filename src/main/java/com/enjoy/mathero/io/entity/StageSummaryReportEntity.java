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
    void setUserId(String userId);
    void setStageNumber(Integer stageNumber);
    void setEasyCorrect(Integer easyCorrect);
    void setEasyTotal(Integer easyTotal);
    void setMediumCorrect(Integer mediumCorrect);
    void setMediumTotal(Integer mediumTotal);
    void setHardCorrect(Integer hardCorrect);
    void setHardTotal(Integer hardTotal);
}