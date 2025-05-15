package com.example.demo.api.service;

import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.Guest;

import java.util.Optional;

public interface IGuestService {
    Optional<Guest> findByAccount(Account account);

}
