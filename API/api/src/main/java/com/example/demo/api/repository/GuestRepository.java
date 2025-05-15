package com.example.demo.api.repository;

import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, String> {
    Optional<Guest> findByAccount(Account account);
}
