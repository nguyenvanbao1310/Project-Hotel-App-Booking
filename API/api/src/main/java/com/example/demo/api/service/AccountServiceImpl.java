package com.example.demo.api.service;

import com.example.demo.api.dto.AccountDTO;
import com.example.demo.api.entity.Account;
import com.example.demo.api.repository.AccountRepository;
import com.example.demo.api.Security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account addAccount(Account account) {
        if (account != null) {
            String newId = generateAccountId();
            account.setId(newId);
            account.setPassword(PasswordEncoder.hashPassword(account.getPassword()));
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        Optional<Account> existing = accountRepository.findById(account.getId());
        if (existing.isPresent()) {
            Account acc = existing.get();
            acc.setUsername(account.getUsername() != null ? account.getUsername() : acc.getUsername());
            acc.setEmail(account.getEmail() != null ? account.getEmail() : acc.getEmail());
            acc.setPhone(account.getPhone() != null ? account.getPhone() : acc.getPhone());
            return accountRepository.save(acc);
        }
        return null;
    }
    @Override
    public Account updateAccount2(String id, AccountDTO dto) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isPresent()) {
            Account acc = existing.get();
            acc.setUsername(dto.getUsername() != null ? dto.getUsername() : acc.getUsername());
            acc.setEmail(dto.getEmail() != null ? dto.getEmail() : acc.getEmail());
            acc.setPhone(dto.getPhone() != null ? dto.getPhone() : acc.getPhone());
            return accountRepository.save(acc);
        }
        return null;
    }

    @Override
    public boolean deleteAccount(String id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public String generateAccountId() {
        int lastId = accountRepository.findLastAccountId(); // cần viết hàm này
        return String.format("G%03d", lastId + 1);
    }
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) return false;

        Account account = optionalAccount.get();
        if (!PasswordEncoder.checkPassword(oldPassword, account.getPassword())) return false;

        account.setPassword(PasswordEncoder.hashPassword(newPassword));
        accountRepository.save(account);
        return true;
    }


}
