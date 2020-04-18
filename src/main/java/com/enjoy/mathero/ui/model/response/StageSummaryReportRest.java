package com.enjoy.mathero.ui.model.response;

public class StageSummaryReportRest {
    private String userId;
    private String username;
    private int stageNumber;
    private int easyCorrect;
    private int easyTotal;
    private int mediumCorrect;
    private int mediumTotal;
    private int hardCorrect;
    private int hardTotal;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public int getEasyCorrect() {
        return easyCorrect;
    }

    public void setEasyCorrect(int easyCorrect) {
        this.easyCorrect = easyCorrect;
    }

    public int getEasyTotal() {
        return easyTotal;
    }

    public void setEasyTotal(int easyTotal) {
        this.easyTotal = easyTotal;
    }

    public int getMediumCorrect() {
        return mediumCorrect;
    }

    public void setMediumCorrect(int mediumCorrect) {
        this.mediumCorrect = mediumCorrect;
    }

    public int getMediumTotal() {
        return mediumTotal;
    }

    public void setMediumTotal(int mediumTotal) {
        this.mediumTotal = mediumTotal;
    }

    public int getHardCorrect() {
        return hardCorrect;
    }

    public void setHardCorrect(int hardCorrect) {
        this.hardCorrect = hardCorrect;
    }

    public int getHardTotal() {
        return hardTotal;
    }

    public void setHardTotal(int hardTotal) {
        this.hardTotal = hardTotal;
    }
}
