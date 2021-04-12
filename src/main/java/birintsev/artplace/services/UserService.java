package birintsev.artplace.services;

import birintsev.artplace.dto.RegistrationRequest;
import birintsev.artplace.model.db.User;
import birintsev.artplace.services.exceptions.UserExistException;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This is a <strong>business logic</strong> providing interface.
 * */
@Service
public interface UserService {

    /**
     * Registers new user + sends a confirmation e-mail.
     * */
    User register(RegistrationRequest registrationRequest)
        throws UserExistException;

    /**
     * This method does not take care about user statuses, states and so on.
     * It just returns all the users.
     *
     * @return all the users registered in the system
     * */
    Set<User> allUsers();

    /**
     *
     * @throws NoSuchElementException if the {@code token} does not exist
     *                                or it's got expired
     * @throws UserExistException     if the users already confirmed
     * */
    void confirm(String token) throws UserExistException;
}
