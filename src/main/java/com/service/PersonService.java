package com.service;

import com.dao.Dao;
import com.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component("personService")
@org.springframework.stereotype.Service
@Transactional
public class PersonService implements Service<Person> {

    @Resource(name = "personDao")
    private Dao<Person> personDao;


    @Override
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
           personDao.deleteById(id);
    }

    @Override
    public Person findById(Long id) {
        return personDao.findById(id);
    }

    @Override
    public Long insert(Person person) {
        return personDao.insert(person);
    }

    @Override
    public Long update(Person person, Long id) {
        return personDao.update(person,id);
    }
}
