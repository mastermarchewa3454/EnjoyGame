package com.enjoy.mathero.shared.dto;

/**
 * Class used to transfer stage summary report data between layers
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class StageSummaryReportDto {
    private String userId;
    private String username;
    private int stageNumber;
    private int easyCorrect;
    private int mediumCorrect;
    private int hardCorrect;
    private int easyTotal;
    private int mediumTotal;
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

    public int getMediumCorrect() {
        return mediumCorrect;
    }

    public void setMediumCorrect(int mediumCorrect) {
        this.mediumCorrect = mediumCorrect;
    }

    public int getHardCorrect() {
        return hardCorrect;
    }

    public void setHardCorrect(int hardCorrect) {
        this.hardCorrect = hardCorrect;
    }

    public int getEasyTotal() {
        return easyTotal;
    }

    public void setEasyTotal(int easyTotal) {
        this.easyTotal = easyTotal;
    }

    public int getMediumTotal() {
        return mediumTotal;
    }

    public void setMediumTotal(int mediumTotal) {
        this.mediumTotal = mediumTotal;
    }

    public int getHardTotal() {
        return hardTotal;
    }

    public void setHardTotal(int hardTotal) {
        this.hardTotal = hardTotal;
    }
}
