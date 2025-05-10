package com.example.hotel_project.model;

import java.io.Serializable;

public class GuestDTO implements Serializable {
    private String id;
    private String fullname;
    private String cccd;
    private String address;
    private Boolean gender;
    private String account_id;


    public GuestDTO() {
    }

    public GuestDTO(String id, String fullname, String cccd, String address, Boolean gender, String account_id) {
        this.id = id;
        this.fullname = fullname;
        this.cccd = cccd;
        this.address = address;
        this.gender = gender;
        this.account_id = account_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
}
