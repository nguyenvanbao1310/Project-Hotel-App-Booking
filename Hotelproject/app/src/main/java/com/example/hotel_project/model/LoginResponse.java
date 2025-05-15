package com.example.hotel_project.model;

public class LoginResponse {
    private String message;
    private AccountDTO account;
    private GuestDTO guest; // Thêm thuộc tính guest

    public String getMessage() {
        return message;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public GuestDTO getGuest() {
        return guest; // Phương thức getter cho GuestDTO
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public void setGuest(GuestDTO guest) {
        this.guest = guest;
    }
}
