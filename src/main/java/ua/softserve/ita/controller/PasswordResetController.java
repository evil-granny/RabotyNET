package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.UserResetPasswordDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.exception.UserNotFoundException;
import ua.softserve.ita.model.User;
import ua.softserve.ita.resetpassword.OnRestorePasswordCompleteEvent;
import ua.softserve.ita.resetpassword.RestorePasswordListener;
import ua.softserve.ita.service.UserService;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/password")
public class PasswordResetController {

    private final UserDao userDao;

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenService tokenService;
    private final RestorePasswordListener restorePasswordListener;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordResetController(UserDao userDao, UserService userService, VerificationTokenService verificationTokenService, VerificationTokenService tokenService, RestorePasswordListener restorePasswordListener, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.tokenService = tokenService;
        this.restorePasswordListener = restorePasswordListener;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody UserResetPasswordDto userResetPasswordDto, final HttpServletRequest request) {
        User user = userDao.getUserWithRoles(userResetPasswordDto.getUsername()).orElseThrow(() ->
                new UserNotFoundException("Please check the correctness of the email " + userResetPasswordDto.getUsername()));
        restorePasswordListener.onApplicationEvent(new OnRestorePasswordCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body("User found successfully.");
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @PostMapping("/change")
    public ResponseEntity<?> changePassword(@RequestBody UserResetPasswordDto userResetPasswordDto) {
        final String result = tokenService.validateVerificationToken(userResetPasswordDto.getUserResetPasswordToken());
        if (result.equals("valid")) {
            verificationTokenService.findByToken(userResetPasswordDto.getUserResetPasswordToken()).ifPresent((t) -> {
                User user = t.getUser();
                user.setPassword(bCryptPasswordEncoder.encode(userResetPasswordDto.getResetPassword()));
                userService.update(user);
            });
        } else {
            throw new ResourceNotFoundException("Your token invalid or expired. Please try again");
        }
        return ResponseEntity.ok().body("Successfully!");
    }

}
