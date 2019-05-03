package ua.softserve.ita.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;
import ua.softserve.ita.registration.OnRegistrationCompleteEvent;
import ua.softserve.ita.service.UserIService;
import ua.softserve.ita.service.token.VerificationTokenIService;
import ua.softserve.ita.service.GenerateLetter;
import ua.softserve.ita.service.Service;
import ua.softserve.ita.service.VerificationTokenIService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class RegistrationController {

    @Autowired
    GenerateLetter generateService;

    @Resource(name = "userService")
    private Service<User> userService;
    private final UserIService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final VerificationTokenIService tokenService;

    public RegistrationController(ApplicationEventPublisher eventPublisher, UserIService userService, VerificationTokenIService tokenService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getPerson(@PathVariable("id") long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody User user) {
        User currentUser = userService.findById(id);
        currentUser.setLogin(user.getLogin());
        currentUser.setEnabled(user.isEnabled());
        userService.update(currentUser);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        User user = userService.findById(id);
        VerificationToken verificationToken = tokenService.findByUser(user);
        tokenService.delete(verificationToken);
        userService.deleteById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> insert(@RequestBody @Valid UserDto userDto, final HttpServletRequest request) {
        User user = userService.create(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body(user);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
