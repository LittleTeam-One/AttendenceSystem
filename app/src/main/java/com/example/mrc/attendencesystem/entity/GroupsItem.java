package com.example.mrc.attendencesystem.entity;

/**
 * Created by Mr.C on 2018/5/15.
 */

public class GroupsItem {
    String imgSrc;
    String username;
    String activeTime;

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
