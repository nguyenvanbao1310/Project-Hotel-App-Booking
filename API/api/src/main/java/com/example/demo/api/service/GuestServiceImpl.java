package com.example.demo.api.service;

import com.example.demo.api.dto.GuestDTO;
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


    public GuestDTO updateGuest(String id, GuestDTO guestDTO) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isPresent()) {
            Guest guest = optionalGuest.get();

            guest.setFullname(guestDTO.getFullname());
            guest.setAddress(guestDTO.getAddress());

            Guest updated = guestRepository.save(guest);

            GuestDTO updatedDTO = new GuestDTO();
            updatedDTO.setId(updated.getId());
            updatedDTO.setFullname(updated.getFullname());
            updatedDTO.setAddress(updated.getAddress());

            return updatedDTO;
        } else {
            throw new RuntimeException("Không tìm thấy guest với id: " + id);
        }
    }
}

