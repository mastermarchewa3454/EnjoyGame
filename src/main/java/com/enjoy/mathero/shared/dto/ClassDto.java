package com.enjoy.mathero.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Class used to transfer class data between layers
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class ClassDto implements Serializable {
    private static final long serialVersionUID = 5049953704840759694L;

    private long id;
    private String classId;
    private String className;
    private List<UserDto> students;
    private UserDto teacherDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<UserDto> getStudents() {
        return students;
    }

    public void setStudents(List<UserDto> students) {
        this.students = students;
    }

    public UserDto getTeacherDetails() {
        return teacherDetails;
    }

    public void setTeacherDetails(UserDto teacherDetails) {
        this.teacherDetails = teacherDetails;
    }
}
