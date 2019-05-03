package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;
import ua.softserve.ita.service.GenerateLetter;
import ua.softserve.ita.service.Service;
import ua.softserve.ita.service.VerificationTokenIService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class RegistrationController {

    @Autowired
    GenerateLetter generateService;

    @Resource(name = "userService")
    private Service<User> userService;

    @Resource(name = "tokenService")
    private VerificationTokenIService verificationTokenIService;


    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
        userService.update(user);
        return ResponseEntity.ok().body("User has been updated successfully.");
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().body("User has been deleted successfully.");
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<User> insert(@RequestBody @Valid User user, final HttpServletRequest request) {
        System.out.println("[registration]");
        User userWithId = userService.create(user);
        String token = UUID.randomUUID().toString();
        VerificationToken vToken =  verificationTokenIService.createVerificationTokenForUser(userWithId,token);
        String confirmationUrl = getAppUrl(request) + "/profile?token=" + vToken.getToken();

        generateService.sendValidationEmail(user, confirmationUrl);

        return ResponseEntity.ok().body(user);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
