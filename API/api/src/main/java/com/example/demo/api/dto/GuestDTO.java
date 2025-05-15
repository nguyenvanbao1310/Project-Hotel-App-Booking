package com.example.demo.api.dto;

import com.example.demo.api.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {
    private String id;
    private String fullname;
    private String cccd;
    private String address;
    private Boolean gender;
    private String account_id;
    public GuestDTO(Guest guest) {
        this.id = guest.getId() != null ? guest.getId().toString() : null;
        this.fullname = guest.getFullname();
        this.cccd = guest.getCccd();
        this.address = guest.getAddress();
        this.gender = guest.getGender();
        this.account_id = guest.getAccount() != null ? guest.getAccount().getId().toString() : null;
    }

}

