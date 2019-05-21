package ua.softserve.ita.resetpassword.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.model.User;
import ua.softserve.ita.registration.RegistrationListener;
import ua.softserve.ita.resetpassword.OnRestorePasswordCompleteEvent;
import ua.softserve.ita.resetpassword.RestorePasswordListener;
import ua.softserve.ita.resetpassword.UserResetPasswordDto;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
public class PasswordResetController {

    private final UserDao userDao;
    private final VerificationTokenService tokenService;
    private final RestorePasswordListener restorePasswordListener;

    public PasswordResetController(UserDao userDao, VerificationTokenService tokenService, RestorePasswordListener restorePasswordListener) {
        this.userDao = userDao;
        this.tokenService = tokenService;
        this.restorePasswordListener = restorePasswordListener;
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserResetPasswordDto userResetPasswordDto, final HttpServletRequest request) throws UserNotFoundException {
        log.info("userLogin = " + userResetPasswordDto.getUsername());
        User user = userDao.findUserByUsername(userResetPasswordDto.getUsername());
        if (user == null) {
            throw new UserNotFoundException("There is no such login user!");
        }
        restorePasswordListener.onApplicationEvent(new OnRestorePasswordCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body("User found successfully.");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping(value = "/changePassword")
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
