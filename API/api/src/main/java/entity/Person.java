package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public abstract class  Person {
    @Id
    private String id;

    private String fullName;
    private String CCCD;
    private String address;
    private Boolean gender;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Account account;

    public Person(String id, String fullName, String CCCD, String address, Boolean gender) {
        this.id = id;
        this.fullName = fullName;
        this.CCCD = CCCD;
        this.address = address;
        this.gender = gender;
    }
}
