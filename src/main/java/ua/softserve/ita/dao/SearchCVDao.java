package ua.softserve.ita.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;


@Component("searchCVDao")
@Repository
public class SearchCVDao {

    Logger LOGGER = Logger.getLogger("ua.softserve.ita.dao.SearchCVDao");

    @Autowired
    private SessionFactory sessionFactory;

    // Search from Person table
    public List<Person> searchByParameter(String parameter, String searchText) {
        if (parameter.equals("firstName")) {
            return searchResult("first_name", searchText);
        } else if (parameter.equals("lastName")) {
            return searchResult("last_name", searchText);
        } else {
            return null;
        }
    }

    // Search from Person table
    private List<Person> searchResult(String parameter, String searchText) {
        Query query = sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM person WHERE " + parameter + " ILIKE :text", Person.class);
        query.setParameter("text", searchText + "%");
        return query.getResultList();
    }
}