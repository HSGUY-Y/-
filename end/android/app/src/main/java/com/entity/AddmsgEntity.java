package com.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class AddmsgEntity implements Serializable {
    private int msgID;
    private String sendacc;
    private String resiveacc;
    private String msg;
    private Timestamp sendtime;
    private String addmsgstatus;
    private String status;
    private String nickname;
    private String headshot;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public int getMsgID() {
        return msgID;
    }

    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    public String getSendacc() {
        return sendacc;
    }

    public void setSendacc(String sendacc) {
        this.sendacc = sendacc;
    }

    public String getResiveacc() {
        return resiveacc;
    }

    public void setResiveacc(String resiveacc) {
        this.resiveacc = resiveacc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getSendtime() {
        return sendtime;
    }

    public void setSendtime(Timestamp sendtime) {
        this.sendtime = sendtime;
    }

    public String getAddmsgstatus() {
        return addmsgstatus;
    }

    public void setAddmsgstatus(String addmsgstatus) {
        this.addmsgstatus = addmsgstatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
