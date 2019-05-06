package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.Query;
import java.util.List;

@Component
@Slf4j
public class SearchCVDao {

    private static final String NAME_QUERY =
            "SELECT * FROM person WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText";
    private static final String PHONE_QUERY =
            "SELECT * FROM person JOIN contacts ON person.user_id = contacts.contacts_id " +
                    "WHERE contacts.phone_number ILIKE :searchText";
    private static final String CITY_QUERY =
            "SELECT * FROM person JOIN address ON person.user_id = address.address_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String ALL_QUERY ="";
    private Session session;

    @Autowired
    public SearchCVDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public List<Person> search(String searchParameter, String searchText, int resultsOnPage, int firstResultNumber) {
        String nativeQuery;
        switch (searchParameter) {
            case "name":
                nativeQuery = NAME_QUERY;
                break;
            case "phoneNumber":
                nativeQuery = PHONE_QUERY;
                break;
            case "city":
                nativeQuery = CITY_QUERY;
                break;
            default:
                nativeQuery = ALL_QUERY;
        }
        Query query = session.createNativeQuery(nativeQuery, Person.class);
        query.setParameter("searchText", "%" + searchText + "%");
        query.setFirstResult(firstResultNumber);
        query.setMaxResults(resultsOnPage);
        List<Person> result = query.getResultList();
        for (Person person : result) {
            log.info(person.toString());
        }
        return result;
    }

}
