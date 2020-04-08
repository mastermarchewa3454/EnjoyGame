package com.enjoy.mathero.io.entity;

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
}
