package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.service.Service;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PersonController {

    @Resource(name = "personService")
    private Service<Person> personService;

    @RequestMapping(method=RequestMethod.GET, value="/admin")
    public String index(){
        return "admin";
    }

    @GetMapping(value = "/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(person);
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
