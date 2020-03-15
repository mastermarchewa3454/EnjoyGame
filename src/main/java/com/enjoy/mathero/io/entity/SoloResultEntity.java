package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="solo_results")
public class SoloResultEntity implements Serializable {
    private static final long serialVersionUID = 4837288633221331456L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String resultId;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private int stageNumber;

    @Column(nullable = false)
    private float time;

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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
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
