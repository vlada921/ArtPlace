package birintsev.artplace.eventslisteners;

import birintsev.artplace.model.db.RegistrationConfirmation;
import birintsev.artplace.model.db.repo.RegistrationRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Locale;
import java.util.NoSuchElementException;

@Component
public class SendRegistrationConfirmationListener
implements ApplicationListener<SendRegistrationConfirmationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        SendRegistrationConfirmationListener.class
    );

    private static final Locale LOCALE_DEFAULT = Locale.US;

    private final MessageSource messageSource;

    private final JavaMailSender mailSender;

    private final RegistrationRepo registrationRepo;

    private final String applicationUrl;

    public SendRegistrationConfirmationListener(
        MessageSource messageSource,
        JavaMailSender mailSender,
        RegistrationRepo registrationRepo,
        @Value("${ap.application.url}")
        String applicationUrl
    ) {
        this.messageSource = messageSource;
        this.mailSender = mailSender;
        this.registrationRepo = registrationRepo;
        this.applicationUrl = applicationUrl;
    }

    @Override
    public void onApplicationEvent(SendRegistrationConfirmationEvent event) {
        final String emailAddr = event.getSource().getEmail();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailAddr);
        msg.setSubject(
            messageSource.getMessage(
                "registration.mail.subject",
                Collections.emptyList().toArray(),
                LOCALE_DEFAULT
            )
        );
        msg.setText(
            messageSource.getMessage(
                "registration.mail.body",
                new Object[] {
                    event.getSource().getName(),
                    registrationConfirmationURL(
                        registrationRepo.findByEmail(emailAddr)
                            .orElseThrow(
                                () -> new NoSuchElementException(
                                    String.format(
                                        "User with email %s"
                                            + " not found in database",
                                        emailAddr
                                    )
                                )
                            )
                    )
                },
                LOCALE_DEFAULT
            )
        );
        mailSender.send(msg);
    }

    private URL registrationConfirmationURL(
        RegistrationConfirmation registrationConfirmation
    ) {
        try {
            return new URL(
                applicationUrl
                    + "/registration-confirmation?token="
                    + URLEncoder.encode(
                        registrationConfirmation.getToken(),
                        Charset.defaultCharset()
                    )
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
