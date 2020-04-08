package com.enjoy.mathero.ui.model.response;

import java.util.List;

public class ClassRest {
    private String classId;
    private String className;
    private String teacher;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<StudentRest> getStudents() {
        return students;
    }

    public void setStudents(List<StudentRest> students) {
        this.students = students;
    }
}
