package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin
@RestController
public class LoginController {

    @RequestMapping("/logedUser")
    public Principal user(Principal user) {
        return user;
    }
}
