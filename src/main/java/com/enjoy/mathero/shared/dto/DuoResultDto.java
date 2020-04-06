package com.enjoy.mathero.shared.dto;

public class DuoResultDto {
    private long id;
    private String resultId;
    private int score;
    private int stageNumber;
    private UserDto userDetails1;
    private UserDto userDetails2;

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

    public UserDto getUserDetails1() {
        return userDetails1;
    }

    public void setUserDetails1(UserDto userDetails1) {
        this.userDetails1 = userDetails1;
    }

    public UserDto getUserDetails2() {
        return userDetails2;
    }

    public void setUserDetails2(UserDto userDetails2) {
        this.userDetails2 = userDetails2;
    }
}
