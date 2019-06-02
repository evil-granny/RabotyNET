package ua.softserve.ita.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import ua.softserve.ita.exception.UserNotFoundException;
import ua.softserve.ita.model.UserPrincipal;

import java.util.Optional;

public class LoggedUserUtil {

    public static Optional<UserPrincipal> getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return Optional.of((UserPrincipal) principal);
        }

        throw new UserNotFoundException("No such user");
    }

}
