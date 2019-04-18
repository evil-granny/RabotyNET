package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.Service;
import ua.softserve.ita.service.UserDetailsService;
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

//    @GetMapping(value = "/person/{id}")
//    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
//        Person person = personService.findById(id);
//        return ResponseEntity.ok().body(person);
//    }

    @GetMapping(value = "/person/{text}")
    public ResponseEntity<UserDetails> showPerson(@PathVariable("text") String username) {
        UserDetails user = userDetails.loadUserByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAll() {
        List<Person> people = personService.findAll();
        return ResponseEntity.ok().body(people);
    }

    @PutMapping("/updatePerson/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Person person) {
        personService.update(person, id);
        return ResponseEntity.ok().body("Person has been updated successfully.");
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        personService.deleteById(id);
        return ResponseEntity.ok().body("Person has been deleted successfully.");
    }

    @PostMapping("/insertPerson")
    public ResponseEntity<?> insert(@RequestBody Person person) {
        long id = personService.insert(person);
        return ResponseEntity.ok().body("New Person has been saved with ID:" + id);
    }
}
