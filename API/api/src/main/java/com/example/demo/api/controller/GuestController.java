package com.example.demo.api.controller;


import com.example.demo.api.dto.GuestDTO;
import com.example.demo.api.entity.Guest;
import com.example.demo.api.service.GuestService;
import com.example.demo.api.service.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class GuestController {
    @Autowired
    private GuestService guestService;
    @Autowired
    private GuestServiceImpl guestServiceI;

    @PostMapping("add_user")
    public ResponseEntity<?> createUser(@RequestBody Guest guest) {
        return guestService.createUser(guest);
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<?> getUser(@PathVariable String account_id) {
        return guestService.getUser(account_id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuest(@PathVariable String id, @RequestBody GuestDTO guestDTO) {
        // cập nhật thông tin khách trong DB
        return ResponseEntity.ok(guestServiceI.updateGuest(id, guestDTO));
    }
}
