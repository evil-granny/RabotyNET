package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
@Slf4j
public class SearchDao {

    private static final String SEARCH_TEXT = "searchText";

    private Session session;

    @Autowired
    public SearchDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public BigInteger getCount(String query, String searchText){
        return (BigInteger)session.createNativeQuery(query)
                .setParameter( SEARCH_TEXT, "%" + searchText + "%").getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Object> getResult(String query, String searchText,
                                    int resultsOnPage, int firstResultNumber){
        return session.createNativeQuery(query).setParameter( SEARCH_TEXT, "%" + searchText + "%")
                .setFirstResult(firstResultNumber)
                .setMaxResults(resultsOnPage)
                .getResultList();
    }
}
