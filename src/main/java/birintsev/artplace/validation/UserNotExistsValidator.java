package birintsev.artplace.validation;

import birintsev.artplace.dto.RegistrationRequest;
import birintsev.artplace.model.db.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UserNotExistsValidator
implements ConstraintValidator<UserNotExists, RegistrationRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        UserNotExistsValidator.class
    );

    private final UserRepo userRepo;

    @Override
    public boolean isValid(
        RegistrationRequest registrationRequest,
        ConstraintValidatorContext constraintValidatorContext
    ) {
        return !userRepo.existsByEmail(registrationRequest.getEmail());
    }
}
