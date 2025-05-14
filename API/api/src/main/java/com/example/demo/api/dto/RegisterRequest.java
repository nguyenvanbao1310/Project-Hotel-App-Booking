package com.example.demo.api.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullname;
    private String cccd;
    private String address;
    private Boolean gender;
    private String email;
    private String phone;
    private String username;
    private String password;

}
