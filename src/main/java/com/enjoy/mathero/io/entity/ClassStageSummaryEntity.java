package com.enjoy.mathero.io.entity;

/**
 * Entity to store and retrieve class stage summary details from the database
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public interface ClassStageSummaryEntity {
    String getClassId();
    String getClassName();
    Integer getStageNumber();
    Integer getEasyCorrect();
    Integer getMediumCorrect();
    Integer getHardCorrect();
    Integer getEasyTotal();
    Integer getMediumTotal();
    Integer getHardTotal();
    void setClassId(String classId);
    void setClassName(String className);
    void setStageNumber(Integer stageNumber);
    void setEasyCorrect(Integer easyCorrect);
    void setEasyTotal(Integer easyTotal);
    void setMediumCorrect(Integer mediumCorrect);
    void setMediumTotal(Integer mediumTotal);
    void setHardCorrect(Integer hardCorrect);
    void setHardTotal(Integer hardTotal);
}
