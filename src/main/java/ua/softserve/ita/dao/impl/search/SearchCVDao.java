package ua.softserve.ita.dao.impl.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import ua.softserve.ita.dto.SearchDTO.SearchCVDTO;
import ua.softserve.ita.dto.SearchDTO.SearchCVResponseDTO;
import ua.softserve.ita.service.search.SearchCVMapper;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

@Component
@Slf4j
public class SearchCVDao {

    private static final String NAME_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText ORDER BY first_name";
    private static final String PHONE_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "WHERE contact.phone_number ILIKE :searchText ORDER BY first_name";
    private static final String CITY_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String SKILL_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "JOIN skill ON cv.cv_id = skill.cv_id "+
                    "WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText ORDER BY first_name";
    private static final String POSITION_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "JOIN skill ON cv.cv_id = skill.cv_id "+
                    "WHERE cv.position ILIKE :searchText ORDER BY first_name";
    private static final String NAME_QUERY_COUNT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText";
    private static final String PHONE_QUERY_COUNT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "WHERE contact.phone_number ILIKE :searchText";
    private static final String CITY_QUERY_COUNT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String SKILL_QUERY_CONT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "JOIN skill ON cv.cv_id = skill.cv_id " +
                    "WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText";
    private static final String POSITION_QUERY_CONT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "JOIN cv ON person.user_id = cv.cv_id " +
                    "WHERE cv.position ILIKE :searchText";
    private Session session;

    @Autowired
    public SearchCVDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public SearchCVResponseDTO search(String searchParameter, String searchText,
                                      int resultsOnPage, int firstResultNumber) {
        SearchCVResponseDTO searchCVResponseDTO = new SearchCVResponseDTO();
        List<SearchCVDTO> dtoList = new ArrayList<>();
        String nativeQuery;
        String countQuery;
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
            default:
                nativeQuery = POSITION_QUERY;
                countQuery = POSITION_QUERY_CONT;

        }
        Query getMathes = session.createNativeQuery(countQuery);
        getMathes.setParameter("searchText", "%" + searchText + "%");
        searchCVResponseDTO.setCount((BigInteger) getMathes.getSingleResult());
        log.info("Count = " + searchCVResponseDTO.getCount());

        Query query = session.createNativeQuery(nativeQuery);
        query.setParameter("searchText", "%" + searchText + "%");
        query.setFirstResult(firstResultNumber);
        query.setMaxResults(resultsOnPage);
        List result = query.getResultList();
        SearchCVMapper searchCVMapper = new SearchCVMapper();
        SearchCVDTO searchCVDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object object : result) {

            try {
                searchCVDTO = searchCVMapper.getSearchCVDTO(objectMapper.writeValueAsString(object));
                log.info("searchCVDTO = " + searchCVDTO);
                dtoList.add(searchCVDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        searchCVResponseDTO.setSearchCVDTOs(dtoList);
        return searchCVResponseDTO;
    }
}
