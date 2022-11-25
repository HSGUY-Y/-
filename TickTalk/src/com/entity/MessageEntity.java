package com.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class MessageEntity implements Serializable {
    private int messageID;
    private String sendacc;
    private String resiveacc;
    private String message;
    private String pic;
    private Timestamp sendtime;
    private String msgstatus;
    private String headshot;
    private String nickname;

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

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Timestamp getSendtime() {
        return sendtime;
    }

    public void setSendtime(Timestamp sendtime) {
        this.sendtime = sendtime;
    }

    public String getMsgstatus() {
        return msgstatus;
    }

    public void setMsgstatus(String msgstatus) {
        this.msgstatus = msgstatus;
    }
}
