package birintsev.artplace.services.exceptions;

/**
 * This exception is raised when user registration process went wrong
 * */
public class RegistrationException extends Exception {

    public RegistrationException() {
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }
}
