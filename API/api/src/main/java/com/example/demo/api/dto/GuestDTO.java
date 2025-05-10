package com.example.demo.api.dto;

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
}

