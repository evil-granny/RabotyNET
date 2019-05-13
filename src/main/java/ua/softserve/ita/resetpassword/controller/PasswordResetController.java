package ua.softserve.ita.resetpassword.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.resetpassword.OnRestorePasswordCompleteEvent;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class PasswordResetController {

    private final UserDao userDao;
    private final ApplicationEventPublisher eventPublisher;

    public PasswordResetController(ApplicationEventPublisher eventPublisher, UserDao userDao) {
        this.eventPublisher = eventPublisher;
        this.userDao = userDao;
    }

    @PostMapping("/user/resetPassword")
    @ResponseBody
    public ResponseEntity<User> resetPassword(@RequestBody String userLogin, final HttpServletRequest request) throws UserNotFoundException {
        User user = userDao.findUserByUsername(userLogin);
        if (user == null) {
            throw new UserNotFoundException();
        }
        eventPublisher.publishEvent(new OnRestorePasswordCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body(user);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
