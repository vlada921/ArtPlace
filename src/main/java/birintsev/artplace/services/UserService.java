package birintsev.artplace.services;

import birintsev.artplace.dto.RegistrationRequest;
import birintsev.artplace.model.db.User;
import birintsev.artplace.services.exceptions.UserExistException;
import java.util.Set;

/**
 * This is a <strong>business logic</strong> providing interface.
 * */
public interface UserService {

    /**
     * Registers new user + sends a confirmation e-mail.
     * */
    User register(RegistrationRequest registrationRequest)
        throws UserExistException;

    Set<User> allUsers();
}
