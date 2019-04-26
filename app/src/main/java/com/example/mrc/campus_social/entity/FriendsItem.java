package com.example.mrc.campus_social.entity;

import java.io.Serializable;

/**
 * Created by Mr.C on 2018/5/15.
 */

public class FriendsItem implements Serializable {
    String imgSrc;
    String username;
    String userId;
    int onlineStatus;

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
