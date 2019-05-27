package ua.softserve.ita.dao.impl.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchResumeDTO;
import ua.softserve.ita.dto.SearchDTO.SearchResumeResponseDTO;
import ua.softserve.ita.service.search.SearchResumeMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class SearchResumeDao {

    private static final String SELECT =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "resume.position, resume.resume_id, contact.phone_number, contact.email, address.city " +
                    "FROM person";
    private static final String JOIN_CONTACT = " JOIN contact ON person.user_id = contact.contact_id";
    private static final String JOIN_ADDRESS = " JOIN address ON person.user_id = address.address_id";
    private static final String JOIN_RESUME = " JOIN resume ON person.user_id = resume.user_id";
    private static final String JOIN_SKILL = " JOIN skill ON resume.resume_id = skill.resume_id";
    private static final String NAME =
            " WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText";
    private static final String PHONE =
            " WHERE contact.phone_number ILIKE :searchText";
    private static final String CITY =
            " WHERE address.city ILIKE :searchText ORDER BY";
    private static final String SKILL =
            " WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText";
    private static final String POSITION =
            " WHERE resume.position ILIKE :searchText";
    private static final String BY_NAME = " ORDER BY first_name";
    private static final String BY_LAST_NAME = " ORDER BY last_name";
    private static final String BY_CITY = " ORDER BY address.city";
    private static final String BY_POSITION = " ORDER BY resume.position";
    private static final String BY_PHONE = " ORDER BY contact.phone_number";
    private static final String BY_AGE = " ORDER BY person.birthday";
    private static final String DIRECTION = " DESC";
    private static final String INVERSE_DIRECTION = " ASC";
    private static final String SELECT_COUNT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person";

    private static final String SEARCH_TEXT = "searchText";

    private Session session;

    @Autowired
    public SearchResumeDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    private BigInteger getCount(String query, String searchText){
        return (BigInteger)session.createNativeQuery(query)
                .setParameter( SEARCH_TEXT, "%" + searchText + "%").getSingleResult();
    }

    private List<SearchResumeDTO> getSearchResumeDTOS(List<Object> list) {
        List<SearchResumeDTO> dtoList = new ArrayList<>();
        SearchResumeMapper searchResumeMapper = new SearchResumeMapper();
        SearchResumeDTO searchResumeDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object object : list) {
            try {
                searchResumeDTO = searchResumeMapper.getSearchResumeDTO(objectMapper.writeValueAsString(object));
                log.info("DTO = " + searchResumeDTO);
                dtoList.add(searchResumeDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return dtoList;
    }

    @SuppressWarnings("unchecked")
    private List<Object> getResult(String query, String searchText,
                                  int resultsOnPage, int firstResultNumber){
        return session.createNativeQuery(query)
                .setParameter( SEARCH_TEXT, "%" + searchText + "%")
                .setFirstResult(firstResultNumber)
                .setMaxResults(resultsOnPage)
                .getResultList();
    }

    private String getQuery(Boolean isCount, String searchParameter, String searchSort, String direction) {
        StringBuilder queryBuilder = new StringBuilder();

        if (isCount) {
            queryBuilder.append(SELECT_COUNT);
        } else {
            queryBuilder.append(SELECT).append(JOIN_CONTACT).append(JOIN_ADDRESS).append(JOIN_RESUME);
        }

        switch (searchParameter) {
            case "name":
                queryBuilder.append(NAME);
                break;
            case "phoneNumber":
                if (isCount) {
                    queryBuilder.append(JOIN_CONTACT);
                }
                queryBuilder.append(PHONE);
                break;
            case "city":
                if (isCount) {
                    queryBuilder.append(JOIN_ADDRESS);
                }
                queryBuilder.append(CITY);
                break;
            case "skill":
                if (isCount) {
                    queryBuilder.append(JOIN_RESUME);
                }
                queryBuilder.append(JOIN_SKILL).append(SKILL);
                break;
            default:
                if (isCount) {
                    queryBuilder.append(JOIN_RESUME);
                }
                queryBuilder.append(POSITION);
        }
        if (!isCount) {
            switch (searchSort) {
                case "lastName":
                    queryBuilder.append(BY_LAST_NAME);
                    if ("desc".equals(direction)){
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "city":
                    queryBuilder.append(BY_CITY);
                    if ("desc".equals(direction)){
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "position":
                    queryBuilder.append(BY_POSITION);
                    if ("desc".equals(direction)){
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "phone":
                    queryBuilder.append(BY_PHONE);
                    if ("desc".equals(direction)){
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "age":
                    queryBuilder.append(BY_AGE);
                    if ("desc".equals(direction)){
                        queryBuilder.append(INVERSE_DIRECTION);
                    } else {
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                default:
                    queryBuilder.append(BY_NAME);
                    if ("desc".equals(direction)){
                        queryBuilder.append(DIRECTION);
                    }
            }
        }
        log.info("query = " + queryBuilder.toString());
        return queryBuilder.toString();
    }

    public SearchResumeResponseDTO getResponse(SearchRequestDTO searchRequestDTO) {
        SearchResumeResponseDTO searchResumeResponseDTO = SearchResumeResponseDTO.builder()
                .count(getCount(getQuery(true, searchRequestDTO.getSearchParameter(),
                        searchRequestDTO.getSearchSort(), searchRequestDTO.getDirection())
                        , searchRequestDTO.getSearchText()))
                .searchResumeDTOS(getSearchResumeDTOS(getResult(getQuery(false,
                        searchRequestDTO.getSearchParameter(), searchRequestDTO.getSearchSort()
                        , searchRequestDTO.getDirection())
                        , searchRequestDTO.getSearchText()
                        , searchRequestDTO.getResultsOnPage()
                        , searchRequestDTO.getFirstResultNumber())))
                .build();
        log.info("searchResumeResponseDTO = " + searchResumeResponseDTO.toString());
        return searchResumeResponseDTO;
    }
}
