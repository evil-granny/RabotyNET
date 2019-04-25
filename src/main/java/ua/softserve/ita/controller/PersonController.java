package ua.softserve.ita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.service.ApplicationContextProvider;
import ua.softserve.ita.service.GenerateLetter;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.MailService;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class PersonController {

    @Autowired
    GenerateLetter generateService;

    @Resource(name = "personService")
    private Service<Person> personService;

    @Resource(name = "userService")
    private Service<User> userService;

    @GetMapping(path = {"/person/{id}"})
    public Person findById(@PathVariable("id") long id) {

        generateService.sendPersonEmail(personService.findById(id));
        return personService.findById(id);
    }

    @GetMapping(path = {"/people"})
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping(path = "/createPerson")
    public Person create(@RequestBody Person person) {
        person.setUser(userService.findById(1L));
        return personService.create(person);
    }

    @PutMapping(path = "/updatePerson")
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping(path = "/deletePerson/{id}")
    public void deleteById(@PathVariable("id") long id) {
        personService.deleteById(id);
    }

}
