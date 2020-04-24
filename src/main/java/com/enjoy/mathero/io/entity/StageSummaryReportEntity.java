package com.enjoy.mathero.io.entity;


/**
 * Entity to store and retrieve stage summary report details from the database
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public interface StageSummaryReportEntity {
    String getUserId();
    String getUsername();
    Integer getStageNumber();
    Integer getEasyCorrect();
    Integer getMediumCorrect();
    Integer getHardCorrect();
    Integer getEasyTotal();
    Integer getMediumTotal();
    Integer getHardTotal();
    void setUserId(String userId);
    void setUsername(String username);
    void setStageNumber(Integer stageNumber);
    void setEasyCorrect(Integer easyCorrect);
    void setEasyTotal(Integer easyTotal);
    void setMediumCorrect(Integer mediumCorrect);
    void setMediumTotal(Integer mediumTotal);
    void setHardCorrect(Integer hardCorrect);
    void setHardTotal(Integer hardTotal);
}
