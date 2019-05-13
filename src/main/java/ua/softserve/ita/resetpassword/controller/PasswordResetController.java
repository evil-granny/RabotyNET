package ua.softserve.ita.resetpassword.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.resetpassword.OnRestorePasswordCompleteEvent;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class PasswordResetController {

    private final UserDao userDao;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService tokenService;

    public PasswordResetController(ApplicationEventPublisher eventPublisher, UserDao userDao, VerificationTokenService tokenService) {
        this.eventPublisher = eventPublisher;
        this.userDao = userDao;
        this.tokenService = tokenService;
    }

    @PostMapping("/user/resetPassword")
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestBody String userLogin, final HttpServletRequest request) throws UserNotFoundException {
        User user = userDao.findUserByUsername(userLogin);
        if (user == null) {
            throw new UserNotFoundException();
        }
        eventPublisher.publishEvent(new OnRestorePasswordCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body("User found successfully.");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping(value = "/user/changePassword")
    public RedirectView showChangePasswordPage(@RequestParam("token") String token) {
        String result = tokenService.validateVerificationToken(token);
        RedirectView redirectConfirmPassword = new RedirectView();
        RedirectView redirectLogin = new RedirectView();
        redirectConfirmPassword.setUrl("http://localhost:4200/confirmPassword");
        redirectLogin.setUrl("http://localhost:4200/login");
        if (result != null) {
            return redirectConfirmPassword;
        }
        return redirectLogin;
    }
}
