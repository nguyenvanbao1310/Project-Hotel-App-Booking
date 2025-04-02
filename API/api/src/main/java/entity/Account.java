package entity;

import enums.EnumRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String userName;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private EnumRole role;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
}
