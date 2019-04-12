package com.controller;

import com.model.Person;
import com.service.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AppController {

    @Resource(name = "personService")
    private Service<Person> personService;


}
