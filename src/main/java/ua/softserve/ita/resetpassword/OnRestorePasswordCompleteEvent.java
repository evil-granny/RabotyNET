package ua.softserve.ita.resetpassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.service.token.VerificationTokenService;

import java.util.UUID;

@SuppressWarnings("restorepassword")
public class OnRestorePasswordCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final User user;

    public OnRestorePasswordCompleteEvent(final User user, final String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public User getUser() {
        return user;
    }

    @Component
    public static class RestorePasswordListener implements ApplicationListener<OnRestorePasswordCompleteEvent> {

        private final UserDao userDao;
        private final VerificationTokenService tokenService;
        private final GenerateLetter sendMailService;

        @Autowired
        public RestorePasswordListener(UserDao userDao, VerificationTokenService tokenService, GenerateLetter sendMailService) {
            this.userDao = userDao;
            this.tokenService = tokenService;
            this.sendMailService = sendMailService;
        }

        @Override
        public void onApplicationEvent(final OnRestorePasswordCompleteEvent event) {
            this.confirmRegistration(event);
        }

        private void confirmRegistration(final OnRestorePasswordCompleteEvent event) {
            final User user = event.getUser();
            final String token = UUID.randomUUID().toString();
            tokenService.createVerificationTokenForUser(user, token);
            final String confirmationUrl = event.getAppUrl() + "/profile?token=" + token;
            sendMailService.sendRestoreForgotPasswordEmail(user, confirmationUrl);
        }
    }
}
