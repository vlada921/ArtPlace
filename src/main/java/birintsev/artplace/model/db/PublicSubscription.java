package birintsev.artplace.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ap_publics_subscriptions")
@IdClass(PublicSubscription.PublicSubscriptionId.class)
public class PublicSubscription {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "public_id")
    private UUID publicId;

    @MapsId("userId")
    @ManyToOne
    private User user;

    @Column(name = "subscribed_when")
    private Timestamp subscribedWhen;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PublicSubscriptionId implements Serializable {

        @Id
        @Column(name = "user_id")
        protected UUID userId;

        @Id
        @Column(name = "public_id")
        protected UUID publicId;
    }
}
