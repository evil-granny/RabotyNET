package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.UserPrincipal;
import ua.softserve.ita.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static ua.softserve.ita.model.UserPrincipal.UNKNOWN_USER;
import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@CrossOrigin
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public UserPrincipal userLoginPost() {
        return getLoggedUser().orElse(UNKNOWN_USER);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        /* Getting session and then invalidating it */
        HttpSession session = request.getSession(false);
        if (request.isRequestedSessionIdValid() && session != null) {
            session.invalidate();
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    @RequestMapping(value = "/login/enabled/{email}/", method = RequestMethod.GET)
    public ResponseEntity<?> enabledUser(@PathVariable("email") String email) {
        if(userService.findByEmail(email).isPresent()){
            User user = userService.findByEmail(email).get();
            return ResponseEntity.ok(user.isEnabled());
        }else return ResponseEntity.ok().body("User not found!");
    }
}
