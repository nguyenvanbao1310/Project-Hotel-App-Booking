package com.example.demo.api.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Guest.class, name = "guest"),
        @JsonSubTypes.Type(value = Admin.class, name = "admin")
})
public abstract class  Person {
    @Id
    private String id;
    private String fullname;
    private String cccd;
    private String address;
    private Boolean gender;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Account account;

    public Person(String id, String fullname, String cccd, String address, Boolean gender) {
        this.id = id;
        this.fullname = fullname;
        this.cccd = cccd;
        this.address = address;
        this.gender = gender;
    }
}
