package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import ua.softserve.ita.dto.SearchDTO.SearchCVDTO;
import ua.softserve.ita.dto.SearchDTO.SearchCVResponseDTO;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.Query;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.*;

@Component
@Slf4j
public class SearchCVDao {

    private static final String NAME_QUERY =
            "SELECT * FROM person WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText";
    private static final String PHONE_QUERY =
            "SELECT person.user_id, person.first_name, person.last_name, person.birthday FROM person JOIN contacts ON person.user_id = contacts.contacts_id " +
                    "WHERE contacts.phone_number ILIKE :searchText";
    private static final String CITY_QUERY =
            "SELECT person.user_id, person.first_name, person.last_name, person.birthday FROM person JOIN address ON person.user_id = address.address_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String SKILL_QUERY =
            "SELECT person.user_id, person.first_name, person.last_name, person.birthday FROM person JOIN cv ON person.user_id = cv.user_id JOIN skill ON cv.cv_id = skill.cv_id " +
                    "WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText";
    private static final String NAME_QUERY_COUNT =
            "SELECT COUNT(person.user_id) FROM person WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText";
    private static final String PHONE_QUERY_COUNT =
            "SELECT COUNT(person.user_id) FROM person JOIN contacts ON person.user_id = contacts.contacts_id " +
                    "WHERE contacts.phone_number ILIKE :searchText";
    private static final String CITY_QUERY_COUNT =
            "SELECT COUNT(person.user_id) FROM person JOIN address ON person.user_id = address.address_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String SKILL_QUERY_CONT =
            "SELECT COUNT(person.user_id) FROM person JOIN cv ON person.user_id = cv.user_id JOIN skill ON cv.cv_id = skill.cv_id " +
                    "WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText";
    private Session session;

    @Autowired
    public SearchCVDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public SearchCVResponseDTO search(String searchParameter, String searchText, int resultsOnPage, int firstResultNumber) {
        SearchCVResponseDTO searchCVResponseDTO = new SearchCVResponseDTO();
        SearchCVDTO searchCVDTO = new SearchCVDTO();
        Set<SearchCVDTO> dtoList = new HashSet<>();
        String nativeQuery = "";
        String countQuery = "";
        switch (searchParameter) {
            case "name":
                nativeQuery = NAME_QUERY;
                countQuery = NAME_QUERY_COUNT;
                break;
            case "phoneNumber":
                nativeQuery = PHONE_QUERY;
                countQuery = PHONE_QUERY_COUNT;
                break;
            case "city":
                nativeQuery = CITY_QUERY;
                countQuery = CITY_QUERY_COUNT;
                break;
            case "skill":
                nativeQuery = SKILL_QUERY;
                countQuery = SKILL_QUERY_CONT;
                break;

        }
        Query getMathes = session.createNativeQuery(countQuery);
        getMathes.setParameter("searchText", "%" + searchText + "%");
        log.info("Result = "+ getMathes.getSingleResult() + " " + getMathes.getSingleResult().getClass());
//        log.info("Result = "+ (BigInteger)getMathes.getSingleResult().intValue());

        log.info(searchCVResponseDTO.toString());

        Query query = session.createNativeQuery(nativeQuery, Person.class);
        query.setParameter("searchText", "%" + searchText + "%");
        query.setFirstResult(firstResultNumber);
        query.setMaxResults(resultsOnPage);
        List<Person> result = query.getResultList();
        for (Person person : result) {
            log.info(person.toString());
            searchCVDTO.setFirstName(person.getFirstName());
            searchCVDTO.setLastName(person.getLastName());
            searchCVDTO.setAge(Period.between(person.getBirthday(), LocalDate.now()).getYears());
            searchCVDTO.setCity("NY");
            searchCVDTO.setEMail("email@sdf.df");
            searchCVDTO.setPhoneNumber("+380953226633");
            searchCVDTO.setPosition("Position");
            searchCVDTO.setId(1);
            dtoList.add(searchCVDTO);
            log.info("DTO = " + searchCVDTO.toString());
        }
        searchCVResponseDTO.setSearchCVDTOs(dtoList);
        return searchCVResponseDTO;
    }

}
