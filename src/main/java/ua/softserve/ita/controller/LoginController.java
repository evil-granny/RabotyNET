package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class LoginController {

    @RequestMapping("/logedUser")
    public Principal user(Principal user) {
        System.out.println("LOGEDUSEWR");
        System.out.println(user);
        return user;
    }

    @PostMapping("/searchCV")
    public Principal userPost(@RequestBody String user) {
        System.out.println("LOGEDUSEWR");
        System.out.println(user);
        return null;
    }
}
