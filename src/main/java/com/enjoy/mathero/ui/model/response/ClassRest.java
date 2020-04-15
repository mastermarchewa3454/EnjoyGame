package com.enjoy.mathero.ui.model.response;

import java.util.List;

public class ClassRest {
    private String classId;
    private String className;
    private String teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private List<StudentRest> students;

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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public List<StudentRest> getStudents() {
        return students;
    }

    public void setStudents(List<StudentRest> students) {
        this.students = students;
    }
}
