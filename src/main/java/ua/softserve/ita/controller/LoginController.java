package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.service.Service;
import ua.softserve.ita.service.UserDetailsServiceImp;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {

    @Resource(name = "personService")
    private Service<Person> personService;

    @Resource(name = "userDetailsService")
    private UserDetailsServiceImp userDetails;

    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("welcomePage");
        return model;
    }

    @RequestMapping(value = { "/homePage"}, method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("homePage");
        return model;
    }

    @RequestMapping(value = {"/userPage"}, method = RequestMethod.GET)
    public ModelAndView userPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("userPage");
        return model;
    }

    @RequestMapping(value = {"/cownerPage"}, method = RequestMethod.GET)
    public ModelAndView cownerPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("cownerPage");
        return model;
    }

    @RequestMapping(value = {"/adminPage"}, method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("adminPage");
        return model;
    }

    @RequestMapping(value = {"/accessDenied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("accessDenied");
        return model;
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
                                  @RequestParam(value = "logout",	required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }

        if (logout != null) {
            model.addObject("message", "Logged out from RabotyNET successfully.");
        }

        model.setViewName("loginPage");
        return model;
    }

    @GetMapping(value = "/personInfoAdmin")
    public ResponseEntity<UserDetails> showAdmin() {
        UserDetails user = userDetails.loadUserByUsername("admin");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/personInfoCompanyOwner")
    public ResponseEntity<UserDetails> showCompanyOwner() {
        UserDetails user = userDetails.loadUserByUsername("cowner");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/personInfoUser")
    public ResponseEntity<UserDetails> showUser() {
        UserDetails user = userDetails.loadUserByUsername("user");
        return ResponseEntity.ok().body(user);
    }
}
