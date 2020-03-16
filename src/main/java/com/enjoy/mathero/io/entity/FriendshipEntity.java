package com.enjoy.mathero.io.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friends")
public class FriendshipEntity implements Serializable {

    private static final long serialVersionUID = -7613386604726422064L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserEntity requester;

    @ManyToOne
    private UserEntity friend;

    @Column
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getRequester() {
        return requester;
    }

    public void setRequester(UserEntity requester) {
        this.requester = requester;
    }

    public UserEntity getFriend() {
        return friend;
    }

    public void setFriend(UserEntity friend) {
        this.friend = friend;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
