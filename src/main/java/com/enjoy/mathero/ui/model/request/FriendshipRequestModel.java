package com.enjoy.mathero.ui.model.request;

public class FriendshipRequestModel {
    private String friendId;
    private Boolean isActive;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
