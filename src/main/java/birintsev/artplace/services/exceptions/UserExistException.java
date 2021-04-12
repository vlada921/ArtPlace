package birintsev.artplace.services.exceptions;

/**
 * Is commonly thrown when it is necessary to markup,
 * that a user being registered already exist.
 *
 * Also, may be used in registration-related cases
 * (that are semantically related)
 * */
public class UserExistException extends RegistrationException {

    public UserExistException() {
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistException(Throwable cause) {
        super(cause);
    }
}
