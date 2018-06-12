package com.example.mrc.attendencesystem.entity;

import java.io.Serializable;

public class GroupSignInMessage implements Serializable{
    private int recordId;//在群消息中id
    private int groupId;//群Id
    private int originatorId;//发起人Id
    private String time; //发起时间
    private double longitude; //发起人经度
    private double latitude; //发起人纬度
    private int region; //签到地理范围
    private int receiverId; //签到人Id
    private double rlongitude; //签到人经度
    private double rlatitude; //签到人纬度
    private boolean state; //签到是否结束
    private boolean done; //签到人是否签到
    private int result; //签到结果

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getOriginatorId() {
        return originatorId;
    }

    public void setOriginatorId(int originatorId) {
        this.originatorId = originatorId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public double getRlongitude() {
        return rlongitude;
    }

    public void setRlongitude(double rlongitude) {
        this.rlongitude = rlongitude;
    }

    public double getRlatitude() {
        return rlatitude;
    }

    public void setRlatitude(double rlatitude) {
        this.rlatitude = rlatitude;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getReceiverId() {

        return receiverId;
    }
}
