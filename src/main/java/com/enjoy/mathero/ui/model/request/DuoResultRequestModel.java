package com.enjoy.mathero.ui.model.request;

/**
 * Request model for duo result
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class DuoResultRequestModel {

    private Integer score;
    private Integer stageNumber;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }
}
