package com.example.demo.api.controller;


import com.example.demo.api.entity.Admin;
import com.example.demo.api.repository.AdminRepository;
import com.example.demo.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("add_admin")
    public Admin createPerson(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }
}
