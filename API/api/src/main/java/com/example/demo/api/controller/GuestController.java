package com.example.demo.api.controller;


import com.example.demo.api.entity.Guest;
import com.example.demo.api.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @PostMapping("add_user")
    public ResponseEntity<?> createUser(@RequestBody Guest guest) {
        return guestService.createUser(guest);
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<?> getUser(@PathVariable String account_id) {
        return guestService.getUser(account_id);
    }
}
