package birintsev.artplace.services;

import birintsev.artplace.dto.RegistrationRequest;
import birintsev.artplace.eventslisteners.SendRegistrationConfirmationEvent;
import birintsev.artplace.model.db.Authority;
import birintsev.artplace.model.db.RegistrationConfirmation;
import birintsev.artplace.model.db.User;
import birintsev.artplace.model.db.repo.RegistrationRepo;
import birintsev.artplace.model.db.repo.UserRepo;
import birintsev.artplace.services.exceptions.UserExistException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        DefaultUserService.class
    );

    private final ApplicationEventPublisher eventPublisher;

    private final UserRepo userRepo;

    private final RegistrationRepo regRepo;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    // todo @transactional
    public User register(RegistrationRequest registrationRequest) {
        User newUser = modelMapper.map(registrationRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser.addAuthority(Authority.REG_CONF_PENDING);

        RegistrationConfirmation registrationConfirmation =
            new RegistrationConfirmation(
                newUser.getId(),
                newUser,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(
                    LocalDateTime.now()
                        .plus(RegistrationConfirmation.DEFAULT_TOKEN_EXPIRATION)
                ),
                UUID.randomUUID().toString()
            );

        // ! order is important
        /* [1] */ userRepo.save(newUser);
        /* [2] */ regRepo.save(registrationConfirmation);
        /* [3] */ eventPublisher.publishEvent(
            new SendRegistrationConfirmationEvent(registrationRequest)
        );

        return newUser;
    }

    @Override
    public Set<User> allUsers() {
        return StreamSupport
            .stream(userRepo.findAll().spliterator(),false)
            .collect(Collectors.toSet());
    }

    @Override
    // todo @transactional
    public void confirm(String token) throws UserExistException {
        User user;
        RegistrationConfirmation registrationConfirmation;
        Optional<RegistrationConfirmation> _regConf =
            regRepo.findByToken(token);
        if (_regConf.isEmpty() || isExpired(_regConf.get())) {
            throw new NoSuchElementException(
                String.format(
                    "The registration (token=%s) can not be confirmed."
                        + " Reasons: it does not exist or has got expired",
                    token
                )
            );
        } else {
            registrationConfirmation = _regConf.get();
            user = registrationConfirmation.getUser();
        }
        if (isConfirmed(registrationConfirmation)) {
            throw new UserExistException(
                "This registration has already been confirmed"
            );
        }
        registrationConfirmation.setConfirmedWhen(
            Timestamp.valueOf(LocalDateTime.now())
        );
        user.addAuthority(Authority.REG_CONFIRMED);
        regRepo.save(registrationConfirmation);
        userRepo.save(user);
    }

    /**
     * <strong>Note!</strong>
     * <p>
     * This method does not take into account
     * if the checked RegistrationConfirmation object
     * has already been confirmed.
     * It only informs if the token
     * <strong>expiration date is in the future or not</strong>.
     * <p>
     * {@code expirationDate = null} means that this token can not be outdated.
     * */
    private boolean isExpired(RegistrationConfirmation regConf) {
        Timestamp expiresWhen = regConf.getExpiresWhen();
        return expiresWhen != null
            && Timestamp.valueOf(LocalDateTime.now()).after(expiresWhen);
    }

    private boolean isConfirmed(RegistrationConfirmation regConf) {
        return regConf.getConfirmedWhen() != null;
    }
}
