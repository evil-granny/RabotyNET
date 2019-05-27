package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.registration.OnRegistrationCompleteEvent;
import ua.softserve.ita.registration.RegistrationListener;
import ua.softserve.ita.service.PersonService;
import ua.softserve.ita.service.UserService;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class RegistrationController {

    private final VerificationTokenService verificationTokenService;
    private final UserService userService;
    private final PersonService personService;
    private final RegistrationListener registrationListener;
    private final VerificationTokenService tokenService;

    public RegistrationController(UserService userService, PersonService personService,
                                  VerificationTokenService tokenService, VerificationTokenService verificationTokenService,
                                  RegistrationListener registrationListener) {
        this.userService = userService;
        this.personService = personService;
        this.tokenService = tokenService;
        this.verificationTokenService = verificationTokenService;
        this.registrationListener = registrationListener;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getPerson(@PathVariable("id") long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/users/{login}/")
    public ResponseEntity<?> getByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok().body(userService.findByEmail(login).isPresent());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody User user) {
        User currentUser = userService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        currentUser.setLogin(user.getLogin());
        currentUser.setEnabled(user.isEnabled());
        userService.update(currentUser);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        tokenService.findByUser(user).ifPresent(tokenService::delete);
        userService.deleteById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> insert(@RequestBody @Valid UserDto userDto, final HttpServletRequest request) {
        User user = userService.createDTO(userDto);

        Person person = new Person();
        person.setUserId(user.getUserId());
        personService.save(person);

        registrationListener.onApplicationEvent(new OnRegistrationCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") final String token) {
        final String result = verificationTokenService.validateVerificationToken(token);
        if (result.equals("TOKEN_VALID")) {
            userService.findByToken(token).ifPresent(this::authWithoutPassword);
        }
        return result;
    }

    @RequestMapping(value = "/user/findToken", method = RequestMethod.GET)
    public String findToken(@RequestParam("username") String username) {
        User user = new User();
        if (userService.findByEmail(username).isPresent()) {
            user = userService.findByEmail(username).get();
        }
        String verificationToken = null;
        if (verificationTokenService.findByUser(user).isPresent()) {
            verificationToken = verificationTokenService.findByUser(user).get().getToken();
        }
        return verificationTokenService.validateVerificationToken(verificationToken);
    }

    @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.POST)
    public ResponseEntity<String> resendRegistrationToken(
            HttpServletRequest request, @RequestParam String email) {

        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Email not found!");
        } else {
            verificationTokenService.deleteByUserId(user.get().getUserId());
            registrationListener.onApplicationEvent(new OnRegistrationCompleteEvent(user.get(), getAppUrl(request)));
            return ResponseEntity.ok().body("Successfully!");
        }
    }

    private void authWithoutPassword(User user) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
