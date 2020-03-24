package com.enjoy.mathero.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDetailsRequestModel {

    @NotNull(message = "Username cannot be null!")
    @Size(max = 50, message = "Username too long!")
    private String username;

    @NotNull(message = "First name cannot be null!")
    @Size(max = 50, message = "First name too long!")
    private String firstName;

    @NotNull(message = "Last name cannot be null!")
    @Size(max = 50, message = "Last name too long!")
    private String lastName;

    @NotNull(message = "Class name cannot be null!")
    private String className;

    @Email(message = "Email must match email address pattern!")
    @Size(max = 120, message = "Email too long!")
    private String email;

    @NotNull(message = "Password cannot be null!")
    @Size(max = 50, message = "Password too long!")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
