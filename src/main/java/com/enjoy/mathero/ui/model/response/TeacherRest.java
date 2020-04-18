package com.enjoy.mathero.ui.model.response;

import java.util.ArrayList;
import java.util.List;

public class TeacherRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String teachClassId;
    private String teachClassName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTeachClassId() {
        return teachClassId;
    }

    public void setTeachClassId(String teachClassId) {
        this.teachClassId = teachClassId;
    }

    public String getTeachClassName() {
        return teachClassName;
    }

    public void setTeachClassName(String teachClassName) {
        this.teachClassName = teachClassName;
    }
}
