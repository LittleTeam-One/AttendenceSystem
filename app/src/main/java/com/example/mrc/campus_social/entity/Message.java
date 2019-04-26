package com.example.mrc.campus_social.entity;

import java.io.Serializable;

/**
 * 信息的实体类
 * Created by Mr.C on 2018/4/18.
 */

public class Message implements Serializable{
    String type;
    String sender;
    String senderNick;
    int senderAvatar;
    String receiver;
    String content;
    String sendTime;
    int groupid;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getSenderNick() {
        return senderNick;
    }
    public void setSenderNick(String senderNick) {
        this.senderNick = senderNick;
    }
    public int getSenderAvatar() {
        return senderAvatar;
    }
    public void setSenderAvatar(int senderAvatar) {
        this.senderAvatar = senderAvatar;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
