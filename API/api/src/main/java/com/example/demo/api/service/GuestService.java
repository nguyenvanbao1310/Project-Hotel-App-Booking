package com.example.demo.api.service;


import com.example.demo.api.dto.AccountDTO;
import com.example.demo.api.dto.GuestDTO;
import com.example.demo.api.dto.ReviewDTO;
import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.Guest;
import com.example.demo.api.mapper.AccountMapper;
import com.example.demo.api.mapper.GuestMapper;
import com.example.demo.api.repository.AccountRepository;
import com.example.demo.api.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Guest> getAllUsers() {
        return guestRepository.findAll();
    }

    public ResponseEntity<?> createUser(@RequestBody Guest guest) {
        if(guestRepository.existsById(guest.getId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Guest with ID " + guest.getId() + " already exists.");
        }
        Guest savedGuest = guestRepository.save(guest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuest);
    }

    public ResponseEntity<?> getUser(@RequestBody String account_id) {
        if(!accountRepository.existsById(account_id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Account with ID " + account_id + "  not found.");
        }
        Account account = accountRepository.findById(account_id).get();

        GuestDTO guestDTO = GuestMapper.toDTO(account);
        return ResponseEntity.ok(guestDTO);
    }
}
