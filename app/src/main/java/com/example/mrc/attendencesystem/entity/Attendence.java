package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;

public class Attendence implements Serializable {
    private static final long serialVersionUID = 10L;
    MessageType messageType;
    User user;
    Group group;
    GroupMessage groupMessage;
    GroupSignInMessage groupSignInMessage;


    public GroupSignInMessage getGroupSignInMessage() {
        return groupSignInMessage;
    }

    public void setGroupSignInMessage(GroupSignInMessage groupSignInMessage) {
        this.groupSignInMessage = groupSignInMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public GroupMessage getGroupMessage() {
        return groupMessage;
    }

    public void setGroupMessage(GroupMessage groupMessage) {
        this.groupMessage = groupMessage;
    }
}
