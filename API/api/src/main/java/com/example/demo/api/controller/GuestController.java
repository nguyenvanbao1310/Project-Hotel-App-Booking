package com.example.demo.api.controller;


import com.example.demo.api.entity.Guest;
import com.example.demo.api.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @PostMapping("add_user")
    public Guest createUser(@RequestBody Guest guest) {
        return guestService.createUser(guest);
    }
}
