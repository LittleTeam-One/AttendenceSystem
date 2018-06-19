package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;

public class Group implements Serializable{
    //private static final long serialVersionUID = 11L;
    int groupId;
    String groupName;
    String introdue;
    String adminId;

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setIntrodue(String introdue) {
        this.introdue = introdue;
    }

    public String getIntrodue() {
        return introdue;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }
}
