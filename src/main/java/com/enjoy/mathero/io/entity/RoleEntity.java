package com.enjoy.mathero.io.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "roles")
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = -1941599417727065268L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

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

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
