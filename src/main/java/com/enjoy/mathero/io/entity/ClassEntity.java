package com.enjoy.mathero.io.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "classes")
public class ClassEntity implements Serializable {
    private static final long serialVersionUID = -8609608429636552094L;

    @Id
    @GeneratedValue
    private long classId;

    @Column(unique = true)
    private String className;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacherDetails;

    @OneToMany(mappedBy = "classDetails")
    private List<UserEntity> students;

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public UserEntity getTeacherDetails() {
        return teacherDetails;
    }

    public void setTeacherDetails(UserEntity teacherDetails) {
        this.teacherDetails = teacherDetails;
    }

    public List<UserEntity> getStudents() {
        return students;
    }

    public void setStudents(List<UserEntity> students) {
        this.students = students;
    }
}
