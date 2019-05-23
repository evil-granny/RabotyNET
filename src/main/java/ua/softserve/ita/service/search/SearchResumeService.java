package ua.softserve.ita.service.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchDao;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchResumeDTO;
import ua.softserve.ita.dto.SearchDTO.SearchResumeResponseDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SearchResumeService implements SearchService<SearchResumeResponseDTO> {

    private static final String NAME_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, cv.cv_id, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText ORDER BY first_name";
    private static final String PHONE_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, cv.cv_id, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "WHERE contact.phone_number ILIKE :searchText ORDER BY first_name";
    private static final String CITY_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, cv.cv_id, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String SKILL_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, cv.cv_id, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "JOIN skill ON cv.cv_id = skill.cv_id " +
                    "WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText ORDER BY first_name";
    private static final String POSITION_QUERY =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "cv.position, cv.cv_id, contact.phone_number, contact.email, address.city " +
                    "FROM person " +
                    "JOIN contact ON person.user_id = contact.contact_id " +
                    "JOIN address ON person.user_id = address.address_id " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "JOIN skill ON cv.cv_id = skill.cv_id " +
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
    private static final String SKILL_QUERY_COUNT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "JOIN skill ON cv.cv_id = skill.cv_id " +
                    "WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText";
    private static final String POSITION_QUERY_COUNT =
            "SELECT DISTINCT COUNT(person.user_id) FROM person " +
                    "JOIN cv ON person.user_id = cv.user_id " +
                    "WHERE cv.position ILIKE :searchText";

    private final SearchDao searchDao;

    @Autowired
    public SearchResumeService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    private BigInteger getCount(String query, String searchText) {
        return searchDao.getCount(query, searchText);
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

    private List<Object> getResult(String query, String searchText, int resultsOnPage, int firstResultNumber) {
        return searchDao.getResult(query, searchText, resultsOnPage, firstResultNumber);
    }

    private String[] getQuery(String searchParameter) {
        switch (searchParameter) {
            case "name":
                return new String[]{NAME_QUERY, NAME_QUERY_COUNT};
            case "phoneNumber":
                return new String[]{PHONE_QUERY, PHONE_QUERY_COUNT};
            case "city":
                return new String[]{CITY_QUERY, CITY_QUERY_COUNT};
            case "skill":
                return new String[]{SKILL_QUERY, SKILL_QUERY_COUNT};
            default:
                return new String[]{POSITION_QUERY, POSITION_QUERY_COUNT};
        }
    }

    @Override
    public SearchResumeResponseDTO getResponse(SearchRequestDTO searchRequestDTO) {

        SearchResumeResponseDTO searchResumeResponseDTO = SearchResumeResponseDTO.builder()
                .count(getCount(getQuery(searchRequestDTO.getSearchParameter())[1], searchRequestDTO.getSearchText()))
                .searchResumeDTOS(getSearchResumeDTOS(getResult(getQuery(searchRequestDTO.getSearchParameter())[0]
                        , searchRequestDTO.getSearchText()
                        , searchRequestDTO.getResultsOnPage()
                        , searchRequestDTO.getFirstResultNumber())))
                .build();
        log.info("searchResumeResponseDTO = " + searchResumeResponseDTO.toString());
        return searchResumeResponseDTO;
    }

}

