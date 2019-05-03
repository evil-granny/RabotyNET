package ua.softserve.ita.registration;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.GenerateLetter;
import ua.softserve.ita.service.token.VerificationTokenIService;

import javax.annotation.Resource;
import java.util.UUID;


@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Resource(name = "tokenService")
    private VerificationTokenIService tokenService;

    @Resource(name = "generateService")
    private GenerateLetter sendMailService;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        tokenService.createVerificationTokenForUser(user, token);
        final String confirmationUrl = event.getAppUrl() + "/profile?token=" + token;
        sendMailService.sendValidationEmail(user, confirmationUrl);
    }

}
