package ua.softserve.ita.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class LoginController {

    @PostMapping("/loginUser")
    public Object userLoginPost() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    @PostMapping("/logoutUser")
    public Object userLogoutPost() {
        return null;
    }
}
