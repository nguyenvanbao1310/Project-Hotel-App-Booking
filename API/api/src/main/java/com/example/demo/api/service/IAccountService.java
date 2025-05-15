package com.example.demo.api.service;

import com.example.demo.api.dto.AccountDTO;
import com.example.demo.api.entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account addAccount(Account account);
    Account updateAccount(Account account);
    boolean deleteAccount(String id);
    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
    Optional<Account> getAccountById(String id);
    List<Account> getAllAccounts();
    String generateAccountId();
    Account updateAccount2(String id, AccountDTO dto);
    boolean changePassword(String email, String oldPassword, String newPassword);


}
