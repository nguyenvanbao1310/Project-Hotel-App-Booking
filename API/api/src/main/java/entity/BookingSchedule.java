package entity;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
public class BookingSchedule {
    @Id
    private String idBookRoom;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account accountBook;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room roomBook;

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
}
