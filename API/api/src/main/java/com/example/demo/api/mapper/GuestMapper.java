package com.example.demo.api.mapper;

import com.example.demo.api.dto.GuestDTO;
import com.example.demo.api.entity.Account;

public class GuestMapper {

    public static GuestDTO toDTO(Account account) {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(account.getPerson().getId());
        guestDTO.setFullname(account.getPerson().getFullname());
        guestDTO.setAddress(account.getPerson().getAddress());
        guestDTO.setGender(account.getPerson().getGender());
        guestDTO.setCccd(account.getPerson().getCccd());
        guestDTO.setAccount_id(AccountMapper.toDTO(account).getId());
        return guestDTO;
    }
}
