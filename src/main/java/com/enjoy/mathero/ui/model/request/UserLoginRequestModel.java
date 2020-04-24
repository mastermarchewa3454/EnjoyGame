package com.enjoy.mathero.ui.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request model for login
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
public class UserLoginRequestModel {

    @NotNull(message = "Username cannot be null!")
    @Size(max = 50, message = "Username too long!")
    private String username;

    @NotNull(message = "Password cannot be null!")
    @Size(max = 50, message = "Password too long!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
