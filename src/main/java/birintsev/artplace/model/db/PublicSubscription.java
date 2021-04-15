package birintsev.artplace.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
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

    @Column(name = "tariff_id", nullable = false)
    private UUID tariffId;

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
