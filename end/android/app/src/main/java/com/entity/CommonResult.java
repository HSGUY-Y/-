package com.entity;

import java.io.Serializable;

public class CommonResult implements Serializable {
    private UserEntity[] userEntities = new UserEntity[200];
    private MessageEntity[] messageEntities = new MessageEntity[200];
    private ContactEntity[] contactEntities = new ContactEntity[200];
    private AddmsgEntity[] addmsgEntities = new AddmsgEntity[200];
    private String flag = null;
    private boolean status = false;
    private boolean conn = false;

    public UserEntity[] getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(UserEntity[] userEntities) {
        this.userEntities = userEntities;
    }

    public MessageEntity[] getMessageEntities() {
        return messageEntities;
    }

    public void setMessageEntities(MessageEntity[] messageEntities) {
        this.messageEntities = messageEntities;
    }

    public ContactEntity[] getContactEntities() {
        return contactEntities;
    }

    public void setContactEntities(ContactEntity[] contactEntities) {
        this.contactEntities = contactEntities;
    }

    public AddmsgEntity[] getAddmsgEntities() {
        return addmsgEntities;
    }

    public void setAddmsgEntities(AddmsgEntity[] addmsgEntities) {
        this.addmsgEntities = addmsgEntities;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isConn() {
        return conn;
    }

    public void setConn(boolean conn) {
        this.conn = conn;
    }
}
