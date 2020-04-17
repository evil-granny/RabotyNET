package ua.com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.VerificationToken;
import ua.com.registration.OnRegistrationCompleteEvent;
import ua.com.registration.RegistrationListener;
import ua.com.service.token.VerificationTokenService;
import ua.com.dto.UserDto;
import ua.com.model.User;
import ua.com.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    private final VerificationTokenService verificationTokenService;
    private final UserService userService;
    private final RegistrationListener registrationListener;

    public RegistrationController(UserService userService, VerificationTokenService verificationTokenService,
                                  RegistrationListener registrationListener) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.registrationListener = registrationListener;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getPerson(@PathVariable("id") long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/username/{login}/")
    public ResponseEntity<?> getByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok().body(userService.findByEmail(login).isPresent());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody User user) {
        User currentUser = userService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        currentUser.setLogin(user.getLogin());
        currentUser.setEnabled(user.isEnabled());
        userService.update(currentUser);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }

    @PostMapping("/auth")
    public ResponseEntity<User> insert(@RequestBody @Valid UserDto userDto, final HttpServletRequest request) {
        User user = userService.createDto(userDto);
        registrationListener.onApplicationEvent(new OnRegistrationCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/auth/confirm")
    public String confirmRegistration(@RequestParam("token") final String token) {
        final String result = verificationTokenService.validateVerificationToken(token);
        if (result.equals("valid")) {
            verificationTokenService.findByToken(token).ifPresent(verificationTokenService::delete);
        }
        return result;
    }

    @GetMapping(value = "/findToken")
    public String findToken(@RequestParam("email") String email) {
        User user = userService.findByEmail(email).orElse(new User());
        VerificationToken verificationToken = verificationTokenService.findByUser(user).orElse(new VerificationToken());
        return verificationTokenService.validateVerificationToken(verificationToken.getToken());
    }

    @PostMapping(value = "/resendAuthToken")
    public ResponseEntity<String> resendRegistrationToken(HttpServletRequest request, @RequestParam String email) {
        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Email not found!");
        } else {
            verificationTokenService.findByUser(user.get()).ifPresent(verificationTokenService::delete);
            registrationListener.onApplicationEvent(new OnRegistrationCompleteEvent(user.get(), getAppUrl(request)));
            return ResponseEntity.ok().body("Successfully!");
        }
    }

    @GetMapping(value = "/enabled/{email}/")
    public ResponseEntity<?> enabledUser(@PathVariable("email") String email) {
        if (userService.findByEmail(email).isPresent()) {
            User user = userService.findByEmail(email).get();
            return ResponseEntity.ok(user.isEnabled());
        } else {
            return ResponseEntity.ok().body("User not found!");
        }
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping(value = "/getRoles/{userName}/")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable("userName") String userName) {
        return ResponseEntity.ok().body(userService.getUserRoles(userName));
    }

}
