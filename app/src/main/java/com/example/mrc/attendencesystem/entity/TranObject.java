package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class TranObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private TranObjectType type;// 发送的消息类型
    private String fromUser;// 来自哪个用户
    private String toUser;// 发往哪个用户
    private User user;// 传输的用户对象，这个对象我们可以自定义任何
    private Group group;//传输的组对象
    private ArrayList<Group> groupList;//传输的组列表对象
    private  boolean isSuccess; //操作是否成功
    private Message message;//传输的消息对象
    private GroupSignInMessage signInfo;//签到信息
    private ArrayList<Message> groupMessages;//消息集合
    private GroupRequest request;//群请求类
    private ArrayList<GroupMessage> groupMessageArrayList;

    public ArrayList<GroupMessage> getGroupMessageArrayList() {
        return groupMessageArrayList;
    }

    public void setGroupMessageArrayList(ArrayList<GroupMessage> groupMessageArrayList) {
        this.groupMessageArrayList = groupMessageArrayList;
    }

    public GroupRequest getRequest() {
        return request;
    }

    public void setRequest(GroupRequest request) {
        this.request = request;
    }


    public ArrayList<GroupSignInMessage> getSignInfoslist() {
        return signInfoslist;
    }
    public void setSignInfoslist(ArrayList<GroupSignInMessage> signInfoslist) {
        this.signInfoslist = signInfoslist;
    }

    public ArrayList<GroupSignInMessage> signInfoslist;
    public ArrayList<Message> getMessages() {
        return groupMessages;
    }
    public void setMessages(ArrayList<Message> messages) {
        this.groupMessages = messages;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public TranObject() {
    }
    public TranObject(TranObjectType type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public TranObjectType getType() {
        return type;
    }
    public void setGroup(Group group2) {
        // TODO Auto-generated method stub
        this.group = group2;

    }
    public void setGroupList(ArrayList<Group> groupsList) {
        // TODO Auto-generated method stub
        this.groupList = groupsList;
    }
    public Group getGroup() {
        // TODO Auto-generated method stub
        return group;
    }
    public String getFromUser() {
        return fromUser;
    }
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
    public String getToUser() {
        return toUser;
    }
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
    public ArrayList<Group> getGroupList() {
        return groupList;
    }
    public Message getMessage() {
        return message;
    }
    public void setMessage(Message message) {
        this.message = message;
    }
    public GroupSignInMessage getSignInfo() {
        return signInfo;
    }
    public void setSignInfo(GroupSignInMessage signInfo) {
        this.signInfo = signInfo;
    }
}
