package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component("searchCVDao")
@Repository
@Slf4j
public class SearchCVDao {

    private SessionFactory sessionFactory;

    @Autowired
    public SearchCVDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<Person> searchByName(String searchText) {
        Query query = sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM person WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText"
                        , Person.class);
        query.setParameter("searchText", searchText + "%");
        List<Person> result = query.getResultList();
        for(Person person : result) {
            log.info(person.toString());
        }
        return result;
    }

    public List<Person> searchByPhone(String searchText) {
        Query query = sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM person " +
                                "JOIN contacts ON person.user_id = contacts.contacts_id " +
                                "WHERE contacts.phone_number ILIKE :searchText"
                        , Person.class);
        query.setParameter("searchText", "%" + searchText + "%");
        List<Person> result = query.getResultList();
        for(Person person : result) {
            log.info(person.toString());
        }
        return result;
    }

    public List<Person> searchByCity(String searchText) {
        Query query = sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM person " +
                                "JOIN address ON person.user_id = address.address_id " +
                                "WHERE address.city ILIKE :searchText"
                        , Person.class);
        query.setParameter("searchText", "%" + searchText + "%");
        List<Person> result = query.getResultList();
        for(Person person : result) {
            log.info(person.toString());
        }
        return result;
    }

    public List<Person> searchBySkill(String searchText) {
        Query query = sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM person " +
                                "JOIN cv ON person.user_id = address.address_id " +
                                "WHERE address.city ILIKE :searchText"
                        , Person.class);
        query.setParameter("searchText", "%" + searchText + "%");
        List<Person> result = query.getResultList();
        for(Person person : result) {
            log.info(person.toString());
        }
        return result;
    }

    public List<Person> searchByAll(String searchText) {
        List<Person> emptyList = new ArrayList<>();
//        Person person = new Person();
//        person.setFirstName("No matching");
//        emptyList.add(person);
        return emptyList;
    }

}
