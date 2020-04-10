package com.enjoy.mathero.ui.model.request;

public class SoloResultRequestModel {

    private Integer score;
    private Integer stageNumber;
    private Integer easyCorrect;
    private Integer mediumCorrect;
    private Integer hardCorrect;
    private Integer easyTotal;
    private Integer mediumTotal;
    private Integer hardTotal;

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

    public Integer getEasyCorrect() {
        return easyCorrect;
    }

    public void setEasyCorrect(Integer easyCorrect) {
        this.easyCorrect = easyCorrect;
    }

    public Integer getMediumCorrect() {
        return mediumCorrect;
    }

    public void setMediumCorrect(Integer mediumCorrect) {
        this.mediumCorrect = mediumCorrect;
    }

    public Integer getHardCorrect() {
        return hardCorrect;
    }

    public void setHardCorrect(Integer hardCorrect) {
        this.hardCorrect = hardCorrect;
    }

    public Integer getEasyTotal() {
        return easyTotal;
    }

    public void setEasyTotal(Integer easyTotal) {
        this.easyTotal = easyTotal;
    }

    public Integer getMediumTotal() {
        return mediumTotal;
    }

    public void setMediumTotal(Integer mediumTotal) {
        this.mediumTotal = mediumTotal;
    }

    public Integer getHardTotal() {
        return hardTotal;
    }

    public void setHardTotal(Integer hardTotal) {
        this.hardTotal = hardTotal;
    }
}
