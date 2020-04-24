package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity to store and retrieve user details from the database
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Entity(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 8617207333750292652L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable=false, length = 50)
    private String firstName;

    @Column(nullable=false, length = 50)
    private String lastName;

    @Column(nullable=false, length = 120)
    private String email;

    @Column(nullable = false)
    private int maxStageCanPlay;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "class_id")
    private ClassEntity classDetails;

    @Column(nullable=false)
    private String encryptedPassword;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SoloResultEntity> soloResults = new ArrayList<>();

    @OneToOne(mappedBy = "teacherDetails", fetch = FetchType.LAZY)
    private ClassEntity teachClasses;

    @OneToMany(mappedBy = "authorDetails")
    private List<CustomLobbyEntity> customLobbies = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")})
    private List<RoleEntity> roles;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public ClassEntity getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(ClassEntity classDetails) {
        this.classDetails = classDetails;
    }

    public List<SoloResultEntity> getSoloResults() {
        return soloResults;
    }

    public void setSoloResults(List<SoloResultEntity> soloResults) {
        this.soloResults = soloResults;
    }

    public ClassEntity getTeachClasses() {
        return teachClasses;
    }

    public void setTeachClasses(ClassEntity teachClasses) {
        this.teachClasses = teachClasses;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<CustomLobbyEntity> getCustomLobbies() {
        return customLobbies;
    }

    public void setCustomLobbies(List<CustomLobbyEntity> customLobbies) {
        this.customLobbies = customLobbies;
    }

    public int getMaxStageCanPlay() {
        return maxStageCanPlay;
    }

    public void setMaxStageCanPlay(int maxStageCanPlay) {
        this.maxStageCanPlay = maxStageCanPlay;
    }
}
