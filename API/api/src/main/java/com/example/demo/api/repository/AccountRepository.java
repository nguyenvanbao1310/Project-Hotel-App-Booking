package com.example.demo.api.repository;


import com.example.demo.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("SELECT MAX(CAST(SUBSTRING(u.id, 2) AS INTEGER)) FROM Account u")
    Integer findLastAccountId();
    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);
}
