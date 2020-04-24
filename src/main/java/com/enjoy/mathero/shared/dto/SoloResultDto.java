package com.enjoy.mathero.shared.dto;

/**
 * Class used to transfer solo result data between layers
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class SoloResultDto {
    private long id;
    private String resultId;
    private int score;
    private int stageNumber;
    private UserDto userDetails;
    private int easyCorrect;
    private int mediumCorrect;
    private int hardCorrect;
    private int easyTotal;
    private int mediumTotal;
    private int hardTotal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public UserDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
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
