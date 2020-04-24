package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity to store and retrieve solo result details from the database
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Entity(name="solo_results")
public class SoloResultEntity implements Serializable {
    private static final long serialVersionUID = 4837288633221331456L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String resultId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int stageNumber;

    @Column(nullable = false)
    private int easyCorrect;

    @Column(nullable = false)
    private int mediumCorrect;

    @Column(nullable = false)
    private int hardCorrect;

    @Column(nullable = false)
    private int easyTotal;

    @Column(nullable = false)
    private int mediumTotal;

    @Column(nullable = false)
    private int hardTotal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;

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

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof SoloResultEntity)) return false;
        return id!=null && id.equals(((SoloResultEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return 43;
    }
}
