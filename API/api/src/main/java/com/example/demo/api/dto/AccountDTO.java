package com.example.demo.api.dto;

import com.example.demo.api.entity.Account;
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
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.role = account.getRole();
    }

    public AccountDTO() {
    }
}
