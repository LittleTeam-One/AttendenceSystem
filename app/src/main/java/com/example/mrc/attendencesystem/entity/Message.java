package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;

/**
 * 信息的实体类
 * Created by Mr.C on 2018/4/18.
 */

public class Message implements Serializable{
    private static final long serialVersionUID = 2L;
    String type;
    String sender;
    String senderNick;
    int senderAvatar;
    int receiver;
    String content;
    String sendTime;

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
    public int getReceiver() {
        return receiver;
    }
    public void setReceiver(int receiver) {
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
}
