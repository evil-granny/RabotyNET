package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class RegistrationController {

    @Resource(name = "userService")
    private Service<User> userService;



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

    @PostMapping("/registration")
    public ResponseEntity<?> insert(@RequestBody @Valid User user) {
        userService.create(user);
        return ResponseEntity.ok().body("New User has been saved");
    }
}
