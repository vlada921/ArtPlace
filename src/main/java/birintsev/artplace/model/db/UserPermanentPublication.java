package birintsev.artplace.model.db;

import birintsev.artplace.model.db.embeddable.UserPublicationAssociation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ap_users_perm_publications")
public class UserPermanentPublication {

    @EmbeddedId
    private UserPublicationAssociation id;
}
