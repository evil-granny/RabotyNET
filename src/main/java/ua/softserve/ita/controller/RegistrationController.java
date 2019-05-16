package ua.softserve.ita.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;
import ua.softserve.ita.registration.OnRegistrationCompleteEvent;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.service.UserService;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class RegistrationController {

    private final GenerateLetter generateService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService tokenService;

    public RegistrationController(GenerateLetter generateService, ApplicationEventPublisher eventPublisher, UserService userService, VerificationTokenService tokenService) {
        this.generateService = generateService;
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getPerson(@PathVariable("id") long id) {
        User user = userService.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody User user) {
        User currentUser = userService.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        currentUser.setLogin(user.getLogin());
        currentUser.setEnabled(user.isEnabled());
        userService.update(currentUser);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        User user = userService.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        VerificationToken verificationToken = tokenService.findByUser(user);
        tokenService.delete(verificationToken);
        userService.deleteById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> insert(@RequestBody @Valid UserDto userDto, final HttpServletRequest request) {
        User user = userService.createDTO(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body(user);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
