package birintsev.artplace.model.db.embeddable;

import birintsev.artplace.model.db.Publication;
import birintsev.artplace.model.db.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class UserPublicationAssociation implements Serializable {

    @ManyToOne
    @JoinColumn(
        name = "user_id", referencedColumnName = "id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
        name = "publication_id", referencedColumnName = "id"
    )
    private Publication publication;
}
