package birintsev.artplace.eventslisteners;

import birintsev.artplace.dto.RegistrationRequest;
import org.springframework.context.ApplicationEvent;

public class SendRegistrationConfirmationEvent extends ApplicationEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param registrationRequest the object on which the event
     *                            initially occurred
     *                            or with which the event is associated
     */
    public SendRegistrationConfirmationEvent(
        RegistrationRequest registrationRequest
    ) {
        super(registrationRequest);
    }

    @Override
    public RegistrationRequest getSource() {
        return (RegistrationRequest) source;
    }
}