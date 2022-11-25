package com.entity;

import java.io.Serializable;

public class ContactEntity implements Serializable {
    private int contactID;
    private String mainacc;
    private String friendacc;
    private String remark;
    private String headshot;
    private String nickname;
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getMainacc() {
        return mainacc;
    }

    public void setMainacc(String mainacc) {
        this.mainacc = mainacc;
    }

    public String getFriendacc() {
        return friendacc;
    }

    public void setFriendacc(String friendacc) {
        this.friendacc = friendacc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
