package com.example.l7.project_chatter.Controllers;

/**
 * Created by L7 on 6/28/15.
 */
public class ContactBean {
    private String name;
    private String phoneNo;

    public ContactBean(String name ,String phoneNo){
        super();
        this.name=name;
        this.phoneNo=phoneNo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


}

