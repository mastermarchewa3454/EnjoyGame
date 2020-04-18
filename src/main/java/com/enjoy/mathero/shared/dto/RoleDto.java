package com.enjoy.mathero.shared.dto;

import java.io.Serializable;

public class RoleDto implements Serializable {
    private static final long serialVersionUID = 2596704671541279394L;

    private long id;
    private String roleName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
