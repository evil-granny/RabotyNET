package com.service;

import com.dao.Dao;
import com.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component("personService")
@org.springframework.stereotype.Service
@Transactional
public class PersonService implements Service<Person> {

    @Resource(name = "personDao")
    private Dao<Person> personDao;


}
