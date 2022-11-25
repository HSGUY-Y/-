package com.entity;

import java.io.Serializable;

public class CommonVo implements Serializable {
    /*最大容量200*/
    private UserEntity[] userEntities = new UserEntity[200];
    private MessageEntity[] messageEntities = new MessageEntity[200];
    private ContactEntity[] contactEntities = new ContactEntity[200];
    private AddmsgEntity[] addmsgEntities = new AddmsgEntity[200];
    private String flag = "";
    private String status ="";

    public CommonVo() {
    }

    public CommonVo(UserEntity[] userEntities, String flag, String status){
        this.userEntities = userEntities;
        this.flag =flag;
        this.status = status;

    }

    public CommonVo(UserEntity[] userEntities, ContactEntity[] contactEntities, String flag, String status) {
        this.userEntities = userEntities;
        this.contactEntities = contactEntities;
        this.flag = flag;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}
