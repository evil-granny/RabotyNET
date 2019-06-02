package ua.softserve.ita.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.service.token.VerificationTokenService;

import java.util.UUID;


@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private static final String FRONT_URL = "http://localhost:4200";

    private final VerificationTokenService tokenService;
    private final GenerateLetter sendMailService;

    @Autowired
    public RegistrationListener(VerificationTokenService tokenService, GenerateLetter sendMailService) {
        this.tokenService = tokenService;
        this.sendMailService = sendMailService;
    }

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        tokenService.createVerificationTokenForUser(user, token);
        final String confirmationUrl = FRONT_URL + "/users/auth/confirm?token=" + token;
        sendMailService.sendValidationEmail(user, confirmationUrl);
    }

}
