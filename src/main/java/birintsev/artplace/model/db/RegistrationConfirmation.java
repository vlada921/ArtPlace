package birintsev.artplace.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ap_registration_confirmation")
public class RegistrationConfirmation {

    public static final Duration DEFAULT_TOKEN_EXPIRATION = Duration.ofDays(1);

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

    @Column(name = "expires_when")
    private Timestamp expiresWhen;

    private String token;
}
