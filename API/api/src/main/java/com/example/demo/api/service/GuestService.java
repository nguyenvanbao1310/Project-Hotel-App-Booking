package com.example.demo.api.service;


import com.example.demo.api.entity.Guest;
import com.example.demo.api.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    public List<Guest> getAllUsers() {
        return guestRepository.findAll();
    }

    public Guest createUser(@RequestBody Guest guest) {
        return guestRepository.save(guest);
    }
}
