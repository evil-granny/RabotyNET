package com.controller;

import com.model.Person;
import com.service.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AppController {

    @Resource(name = "personService")
    private Service<Person> personService;


    @GetMapping(value = "/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(person);

    }

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getAll() {
        List<Person> personList = personService.findAll();
        return ResponseEntity.ok().body(personList);
    }
    @PutMapping("/person/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Person person) {
        personService.update(person,id);
        return ResponseEntity.ok().body("Person has been updated successfully.");
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        personService.deleteById(id);
        return ResponseEntity.ok().body("Person has been deleted successfully.");
    }
    @PostMapping("/person")
    public ResponseEntity<?> insert(@RequestBody Person person) {
        long id = personService.insert(person);
        return ResponseEntity.ok().body("New Person has been saved with ID:" + id);
    }


}
