package com.enjoy.mathero.shared.dto;

import com.enjoy.mathero.io.entity.ClassEntity;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

    private static final long serialVersionUID = -7421968725101561415L;
    private long id;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private ClassDto classDetails;
    private String email;
    private String password;
    private String encryptedPassword;
    private int maxStageCanPlay;
    private List<SoloResultDto> soloResults;
    private ClassDto teachClasses;
    private List<CustomLobbyDto> customLobbies;
    private RoleDto role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public ClassDto getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(ClassDto classDetails) {
        this.classDetails = classDetails;
    }

    public List<SoloResultDto> getSoloResults() {
        return soloResults;
    }

    public void setSoloResults(List<SoloResultDto> soloResults) {
        this.soloResults = soloResults;
    }

    public ClassDto getTeachClasses() {
        return teachClasses;
    }

    public void setTeachClasses(ClassDto teachClasses) {
        this.teachClasses = teachClasses;
    }

    public List<CustomLobbyDto> getCustomLobbies() {
        return customLobbies;
    }

    public void setCustomLobbies(List<CustomLobbyDto> customLobbies) {
        this.customLobbies = customLobbies;
    }

    public int getMaxStageCanPlay() {
        return maxStageCanPlay;
    }

    public void setMaxStageCanPlay(int maxStageCanPlay) {
        this.maxStageCanPlay = maxStageCanPlay;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
