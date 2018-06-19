package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMessage implements Serializable{
    private int messageId;//消息Id
    private int groupId;//群ID
    private String fromId;//发起人ID 即手机号
    private int contentType;//消息类型
    private String content;//消息内容

    public GroupMessage(){

    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
