package ua.softserve.ita.resetpassword.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import ua.softserve.ita.service.UserService;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
public class PasswordResetController {

    private final UserDao userDao;
    private final UserService userService;
    private final VerificationTokenService tokenService;
    private final RestorePasswordListener restorePasswordListener;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordResetController(UserDao userDao, UserService userService, VerificationTokenService tokenService, RestorePasswordListener restorePasswordListener, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.userService = userService;
        this.tokenService = tokenService;
        this.restorePasswordListener = restorePasswordListener;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody UserResetPasswordDto userResetPasswordDto) {
        final String result = tokenService.validateVerificationToken(userResetPasswordDto.getUserResetPasswordToken());
        if (result.equals("valid")) {
            final Optional<User> user = userService.findByToken(userResetPasswordDto.getUserResetPasswordToken());
            log.info("User = " + user);
            user.get().setPassword(bCryptPasswordEncoder.encode(userResetPasswordDto.getResetPassword()));
            log.info("User new password = " + user.get().getPassword());
            userService.update(user.get());
        }
        return ResponseEntity.ok().body("Successfully!");
    }
}
