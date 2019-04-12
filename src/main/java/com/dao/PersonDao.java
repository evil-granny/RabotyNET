package com.dao;

import com.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("personDao")
@Repository
public class PersonDao implements Dao<Person> {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        cq.select(root);
        Query<Person> query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.byId(Person.class).load(id);
        session.delete(person);
    }

    @Override
    public Person findById(Long id) {
        return sessionFactory.getCurrentSession().get(Person.class,id);
    }

    @Override
    public Long insert(Person person) {
        sessionFactory.getCurrentSession().save(person);
        return person.getUserId();
    }

    @Override
    public Long update(Person person, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Person tempPerson = session.byId(Person.class).load(id);
        tempPerson.setFirstName(person.getFirstName());
        tempPerson.setLastName(person.getLastName());
        tempPerson.setBirthday(person.getBirthday());
        tempPerson.setPhoneNumber(person.getPhoneNumber());
        tempPerson.setAddress(person.getAddress());
        session.flush();
        return id;
    }
}
