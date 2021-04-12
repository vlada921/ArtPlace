package birintsev.artplace.model.db.repo;

import birintsev.artplace.model.db.RegistrationConfirmation;
import birintsev.artplace.model.db.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationRepo
extends CrudRepository<RegistrationConfirmation, UUID> {

    Optional<RegistrationRepo> findByUser(User user);

    @Query(
        value =
            "select r"
            + " from RegistrationConfirmation r"
            + " where r.user.email = :email"
    )
    Optional<RegistrationConfirmation> findByEmail(String email);
}
