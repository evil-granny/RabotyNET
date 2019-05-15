package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.model.UserPrincipal;

import static ua.softserve.ita.model.UserPrincipal.UNKNOWN_USER;
import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@CrossOrigin
@RestController
public class LoginController {

    @PostMapping("/loginUser")
    public UserPrincipal userLoginPost() {
        return getLoggedUser().orElse(UNKNOWN_USER);
    }
}
