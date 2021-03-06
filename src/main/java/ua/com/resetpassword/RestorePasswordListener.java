package ua.com.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ua.com.model.User;
import ua.com.service.letter.GenerateLetter;
import ua.com.service.token.VerificationTokenService;

import java.util.UUID;

import static ua.com.config.SecurityConfiguration.FRONT_URL;

@Component
public class RestorePasswordListener implements ApplicationListener<OnRestorePasswordCompleteEvent> {

    private final VerificationTokenService tokenService;
    private final GenerateLetter sendMailService;

    @Autowired
    public RestorePasswordListener(VerificationTokenService tokenService, GenerateLetter sendMailService) {
        this.tokenService = tokenService;
        this.sendMailService = sendMailService;
    }

    @Override
    public void onApplicationEvent(final OnRestorePasswordCompleteEvent event) {
        this.confirmRestorePassword(event);
    }

    private void confirmRestorePassword(final OnRestorePasswordCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        tokenService.createVerificationTokenForUser(user, token);
        final String confirmationUrl = FRONT_URL + "/confirmPassword?token=" + token;
        sendMailService.sendRestoreForgotPasswordEmail(user, confirmationUrl);
    }

}
