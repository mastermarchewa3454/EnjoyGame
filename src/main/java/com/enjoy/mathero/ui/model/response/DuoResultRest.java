package com.enjoy.mathero.ui.model.response;

/**
 * Response model for duo result
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class DuoResultRest {
    private String userId1;
    private String username1;
    private String userId2;
    private String username2;
    private int score;
    private int stageNumber;

    public String getUserId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
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
}
