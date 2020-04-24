package com.enjoy.mathero.ui.model.response;

/**
 * Response model for user
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class UserRest {

    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String className;
    private String classId;
    private int maxStageCanPlay;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getMaxStageCanPlay() {
        return maxStageCanPlay;
    }

    public void setMaxStageCanPlay(int maxStageCanPlay) {
        this.maxStageCanPlay = maxStageCanPlay;
    }
}
