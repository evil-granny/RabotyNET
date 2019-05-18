package ua.softserve.ita.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import ua.softserve.ita.model.UserPrincipal;

import java.util.Optional;

import static ua.softserve.ita.model.UserPrincipal.UNKNOWN_USER;

public class LoggedUserUtil {

    private LoggedUserUtil() {
    }

    public static Optional<UserPrincipal> getLoggedUser(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return Optional.of((UserPrincipal) principal);
        }
        return Optional.of(UNKNOWN_USER);
    }

}
