package com.example.hotel_project.model;
public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public ChangePasswordRequest(String email, String oldPassword, String newPassword, String confirmPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    // Getters và Setters nếu dùng Gson hoặc cần hỗ trợ
}
