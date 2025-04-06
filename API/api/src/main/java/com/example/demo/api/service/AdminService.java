package com.example.demo.api.service;


import com.example.demo.api.entity.Admin;
import com.example.demo.api.entity.Person;
import com.example.demo.api.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }
}
