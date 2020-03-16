package com.enjoy.mathero.shared.dto;

import com.enjoy.mathero.io.entity.UserEntity;

import java.io.Serializable;

public class FriendshipDto implements Serializable {
    private static final long serialVersionUID = 2260106303637414413L;
    private long id;
    private UserDto requester;
    private UserDto friend;
    private Boolean isActive;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getRequester() {
        return requester;
    }

    public void setRequester(UserDto requester) {
        this.requester = requester;
    }

    public UserDto getFriend() {
        return friend;
    }

    public void setFriend(UserDto friend) {
        this.friend = friend;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
