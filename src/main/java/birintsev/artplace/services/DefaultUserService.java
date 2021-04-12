package birintsev.artplace.services;

import birintsev.artplace.dto.RegistrationRequest;
import birintsev.artplace.eventslisteners.SendRegistrationConfirmationEvent;
import birintsev.artplace.model.db.Authority;
import birintsev.artplace.model.db.RegistrationConfirmation;
import birintsev.artplace.model.db.User;
import birintsev.artplace.model.db.repo.RegistrationRepo;
import birintsev.artplace.model.db.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Override
    @Transactional
    public User register(RegistrationRequest registrationRequest) {
        User newUser = modelMapper.map(registrationRequest, User.class);

        newUser.addAuthority(Authority.REG_CONF_PENDING);

        RegistrationConfirmation registrationConfirmation =
            new RegistrationConfirmation(
                newUser.getId(),
                newUser,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
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
}
