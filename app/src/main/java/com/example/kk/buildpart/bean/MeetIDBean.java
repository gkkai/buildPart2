package com.example.kk.buildpart.bean;

/**
 * Created by kk on 2019/1/22.
 */

public class MeetIDBean {

    private  String meeting_id;
    private  String meeting_capacity;
    private  String uid;
    private  String token;
    private  String flag;
    private  String msg;

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getMeeting_capacity() {
        return meeting_capacity;
    }

    public void setMeeting_capacity(String meeting_capacity) {
        this.meeting_capacity = meeting_capacity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
