package com.example.hotel_project.Model;

public class User
{
    String id;
    String userName;
    String passWord;
    String email;
    String phone;

    public User(String id, String userName, String passWord, String email, String phone) {
        super();
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.phone = phone;
    }
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
