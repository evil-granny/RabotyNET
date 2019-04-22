package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class PersonController {

    @Resource(name = "personService")
    private Service<Person> personService;

    @GetMapping(path = {"/person/{id}"})
    public Person findById(@PathVariable("id") long id) {
        return personService.findById(id);
    }

    @GetMapping(path = {"/people"})
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping(path = "/create")
    public Person create(@RequestBody Person person) {
        return personService.insert(person);
    }

    @PutMapping(path = "/update")
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteById(@PathVariable("id") long id) {
        personService.deleteById(id);
    }

}