package ua.softserve.ita.resetpassword;

import org.springframework.context.ApplicationEvent;
import ua.softserve.ita.model.User;

import java.util.Optional;

@SuppressWarnings("restorepassword")
public class OnRestorePasswordCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Optional<User> user;

    public OnRestorePasswordCompleteEvent(final Optional<User> user, final String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Optional<User> getUser() {
        return user;
    }
}
