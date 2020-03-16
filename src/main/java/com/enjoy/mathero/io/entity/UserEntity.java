package com.enjoy.mathero.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Column(length = 50)
    private String classId;

    @Column(nullable=false)
    private String encryptedPassword;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private List<SoloResultEntity> soloResults = new ArrayList<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipEntity> friendsRequests = new ArrayList<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipEntity> friendOf = new ArrayList<>();

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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List<SoloResultEntity> getSoloResults() {
        return soloResults;
    }

    public void setSoloResults(List<SoloResultEntity> soloResults) {
        this.soloResults = soloResults;
    }

    public List<FriendshipEntity> getFriendsRequests() {
        return friendsRequests;
    }

    public void setFriendsRequests(List<FriendshipEntity> friendsRequests) {
        this.friendsRequests = friendsRequests;
    }

    public List<FriendshipEntity> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(List<FriendshipEntity> friendOf) {
        this.friendOf = friendOf;
    }
}
