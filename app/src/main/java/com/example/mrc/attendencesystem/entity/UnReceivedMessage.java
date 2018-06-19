package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnReceivedMessage implements Serializable{
    private static final long serialVersionUID = 3L;
    int groupId;
    String receiverId;
    int state;

    public UnReceivedMessage(int groupId, String receiverId) {
        this.groupId = groupId;
        this.receiverId = receiverId;
    }

    public UnReceivedMessage(ResultSet rs)throws SQLException{
        setGroupId(rs.getInt("groupid"));
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
