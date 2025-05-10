package com.example.demo.api.mapper;

import com.example.demo.api.dto.AccountDTO;
import com.example.demo.api.entity.Account;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setPassword(account.getPassword());
        dto.setEmail(account.getEmail());
        dto.setPhone(account.getPhone());
        dto.setRole(account.getRole());
        return dto;
    }

    public static Account toEntity(AccountDTO dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setUsername(dto.getUsername());
        account.setEmail(dto.getEmail());
        account.setPhone(dto.getPhone());
        account.setRole(dto.getRole());
        return account;
    }
}
