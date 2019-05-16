package ua.softserve.ita.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;
import ua.softserve.ita.registration.OnRegistrationCompleteEvent;
import ua.softserve.ita.registration.RegistrationListener;
import ua.softserve.ita.service.UserService;
import ua.softserve.ita.service.token.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class RegistrationController {

    private final VerificationTokenService verificationTokenService;
    private final UserService userService;
    private final RegistrationListener registrationListener;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService tokenService;

    public RegistrationController(ApplicationEventPublisher eventPublisher, UserService userService, VerificationTokenService tokenService, VerificationTokenService verificationTokenService, RegistrationListener registrationListener) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.tokenService = tokenService;
        this.verificationTokenService = verificationTokenService;
        this.registrationListener = registrationListener;
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getPerson(@PathVariable("id") long id) {
        User user = userService.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("User with id: %d not found", id)));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/users/{login}/")
    public ResponseEntity<?> getByLogin(@PathVariable("login") String login) {
//        if (!userService.findByEmail(login).isEmpty()){
//            return ResponseEntity.ok(userService.findByEmail(login));
//        }
        List<User> users = userService.findByEmail(login);
        return ResponseEntity.ok().body(users);
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
        registrationListener.onApplicationEvent(new OnRegistrationCompleteEvent(user, getAppUrl(request)));
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = verificationTokenService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final Optional<User> user = userService.findByToken(token);

            authWithoutPassword(user);
            model.addAttribute("message", "account verified!");
            return "redirect:/login";
        }

        model.addAttribute("message", "failed");
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return "redirect:/badUser";
    }

    public void authWithoutPassword(Optional<User> user) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
