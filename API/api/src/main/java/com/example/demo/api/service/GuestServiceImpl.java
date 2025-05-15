package com.example.demo.api.service;

import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.Guest;
import com.example.demo.api.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestServiceImpl implements IGuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public Optional<Guest> findByAccount(Account account) {
        return guestRepository.findByAccount(account);
    }
}
