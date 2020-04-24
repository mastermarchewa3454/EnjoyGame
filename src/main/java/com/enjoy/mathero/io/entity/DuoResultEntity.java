package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity to store and retrieve duo result details from the database
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Entity(name = "duo_results")
public class DuoResultEntity implements Serializable {
    private static final long serialVersionUID = -1720073433164520888L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String resultId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int stageNumber;

    @ManyToOne
    @JoinColumn(name = "user_id1")
    private UserEntity userDetails1;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private UserEntity userDetails2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public UserEntity getUserDetails1() {
        return userDetails1;
    }

    public void setUserDetails1(UserEntity userDetails1) {
        this.userDetails1 = userDetails1;
    }

    public UserEntity getUserDetails2() {
        return userDetails2;
    }

    public void setUserDetails2(UserEntity userDetails2) {
        this.userDetails2 = userDetails2;
    }
}
