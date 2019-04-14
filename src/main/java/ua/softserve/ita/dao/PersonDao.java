package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Person;

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
    public Person findById(Long id) {
        return sessionFactory.getCurrentSession().get(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);
        Query<Person> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Long insert(Person person) {
        sessionFactory.getCurrentSession().save(person);
        return person.getUserId();
    }

    @Override
    public Long update(Person person, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Person updatedPerson = session.byId(Person.class).load(id);
        updatedPerson.setFirstName(person.getFirstName());
        updatedPerson.setLastName(person.getLastName());
        updatedPerson.setBirthday(person.getBirthday());
        updatedPerson.setPhoneNumber(person.getPhoneNumber());
        updatedPerson.setAddress(person.getAddress());
        session.flush();

        return id;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.byId(Person.class).load(id);
        session.delete(person);
    }

}
