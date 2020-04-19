package ua.com.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ua.com.model.User;
import ua.com.service.letter.GenerateLetter;
import ua.com.service.token.VerificationTokenService;

import java.util.UUID;

import static ua.com.config.SecurityConfiguration.FRONT_URL;


@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

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
        final String confirmationUrl = "http://localhost:4200/users/auth/confirm?token=" + token;
        sendMailService.sendValidationEmail(user, confirmationUrl);
    }

}
