package com.example.demo.api.dto;

import com.example.demo.api.enums.EnumRole;
import lombok.Data;

@Data
public class AccountDTO {
    private String id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private EnumRole role;
}
