package birintsev.artplace.model.db;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "ap_users_registration_requests")
public class RegistrationConfirmation {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @OneToOne(
        optional = false
    )
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    @MapsId
    private User user;

    @Column(name = "created_when")
    private Timestamp createdWhen;

    @Column(name = "confirmed_when")
    private Timestamp confirmedWhen;

    private String token;
}
