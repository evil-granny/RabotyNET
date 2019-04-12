package com.dao;

import com.model.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("personDao")
@Repository
public class PersonDao implements Dao<Person> {

    @Autowired
    private SessionFactory sessionFactory;


}
