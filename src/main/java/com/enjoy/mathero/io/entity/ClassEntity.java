package com.enjoy.mathero.io.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "classes")
public class ClassEntity implements Serializable {
    private static final long serialVersionUID = -8609608429636552094L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String classId;

    @Column(unique = true)
    private String className;

    @OneToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacherDetails;

    @OneToMany(mappedBy = "classDetails", cascade = CascadeType.ALL)
    private List<UserEntity> students;

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
