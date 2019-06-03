package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchResumeDto;
import ua.softserve.ita.dto.search.SearchResumeResponseDto;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class SearchResumeDao {

    private static final String SELECT =
            "SELECT DISTINCT person.user_id, person.first_Name, person.last_name, person.birthday, " +
                    "resume.position, resume.resume_id, contact.phone_number, address.city " +
                    "FROM resume";
    private static final String JOIN_PERSON = " JOIN person ON resume.user_id = person.user_id";
    private static final String JOIN_CONTACT = " JOIN contact ON person.user_id = contact.contact_id";
    private static final String JOIN_ADDRESS = " JOIN address ON person.user_id = address.address_id";
    private static final String JOIN_SKILL = " JOIN skill ON resume.resume_id = skill.resume_id";
    private static final String NAME =
            " WHERE first_name ILIKE :searchText OR last_name ILIKE :searchText";
    private static final String PHONE =
            " WHERE contact.phone_number ILIKE :searchText";
    private static final String CITY =
            " WHERE address.city ILIKE :searchText";
    private static final String SKILL =
            " WHERE skill.title ILIKE :searchText OR skill.description ILIKE :searchText";
    private static final String POSITION =
            " WHERE resume.position ILIKE :searchText";
    private static final String BY_NAME = " ORDER BY first_name %s, last_name";
    private static final String BY_LAST_NAME = " ORDER BY last_name %s, first_name, last_name";
    private static final String BY_CITY = " ORDER BY address.city %s, first_name, last_name";
    private static final String BY_POSITION = " ORDER BY resume.position %s, first_name, last_name";
    private static final String BY_PHONE = " ORDER BY contact.phone_number %s, first_name, last_name";
    private static final String BY_AGE = " ORDER BY person.birthday %s, first_name, last_name";
    private static final String DESC = "DESC";
    private static final String ASC = "ASC";
    private static final String SELECT_COUNT =
            "SELECT DISTINCT COUNT(resume.resume_id) FROM resume";

    private static final String SEARCH_TEXT = "searchText";

    private Session session;

    @Autowired
    public SearchResumeDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    private BigInteger getCount(String query, String searchText) {
        return (BigInteger) session.createNativeQuery(query)
                .setParameter(SEARCH_TEXT, "%" + searchText + "%").getSingleResult();
    }

    private List<SearchResumeDto> getResult(String query, String searchText,
                                            int resultsOnPage, int firstResultNumber) {

        List<Tuple> tupleList = session.createNativeQuery(query, Tuple.class)
                .setParameter(SEARCH_TEXT, "%" + searchText + "%")
                .setFirstResult(firstResultNumber)
                .setMaxResults(resultsOnPage)
                .getResultList();

        List<SearchResumeDto> dtoList = new ArrayList<>();
        for (Tuple tuple : tupleList) {
            SearchResumeDto searchResumeDto = new SearchResumeDto();
            searchResumeDto.setId(tuple.get("user_id", BigInteger.class));
            try {
                searchResumeDto.setFirstName(tuple.get("first_name", String.class));
            } catch (IllegalArgumentException e) {
                searchResumeDto.setFirstName("");
            }
            try {
                searchResumeDto.setLastName(tuple.get("last_name", String.class));
            } catch (IllegalArgumentException e) {
                searchResumeDto.setLastName("");
            }
            try {
                searchResumeDto.setAge(Period.between(tuple.get("birthday", java.sql.Date.class).toLocalDate(), LocalDate.now()).getYears());
            } catch (IllegalArgumentException e) {
                searchResumeDto.setAge(0);
            }
            try {
                searchResumeDto.setPosition(tuple.get("position", String.class));
            } catch (IllegalArgumentException e) {
                searchResumeDto.setPosition("");
            }
            searchResumeDto.setResumeId(tuple.get("resume_id", BigInteger.class));
            try {
                searchResumeDto.setCity(tuple.get("city", String.class));
            } catch (IllegalArgumentException e) {
                searchResumeDto.setCity("");
            }
            try {
                searchResumeDto.setPhoneNumber(tuple.get("phone_number", String.class));
            } catch (IllegalArgumentException e) {
                searchResumeDto.setPhoneNumber("");
            }
            dtoList.add(searchResumeDto);
        }
        return dtoList;
    }

    private String getQuery(Boolean isCount, String searchParameter, String searchSort, String direction) {
        StringBuilder queryBuilder = new StringBuilder();

        if (isCount) {
            queryBuilder.append(SELECT_COUNT);
        } else {
            queryBuilder.append(SELECT).append(JOIN_PERSON).append(JOIN_CONTACT).append(JOIN_ADDRESS);
        }

        switch (searchParameter) {
            case "name":
                if (isCount) {
                    queryBuilder.append(JOIN_PERSON);
                }
                queryBuilder.append(NAME);
                break;
            case "phoneNumber":
                if (isCount) {
                    queryBuilder.append(JOIN_PERSON);
                    queryBuilder.append(JOIN_CONTACT);
                }
                queryBuilder.append(PHONE);
                break;
            case "city":
                if (isCount) {
                    queryBuilder.append(JOIN_PERSON);
                    queryBuilder.append(JOIN_ADDRESS);
                }
                queryBuilder.append(CITY);
                break;
            case "skill":
                queryBuilder.append(JOIN_SKILL).append(SKILL);
                break;
            default:
                queryBuilder.append(POSITION);
        }
        if (!isCount) {
            switch (searchSort) {
                case "lastName":
                    queryBuilder.append(String.format(BY_LAST_NAME, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "city":
                    queryBuilder.append(String.format(BY_CITY, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "position":
                    queryBuilder.append(String.format(BY_POSITION, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "phone":
                    queryBuilder.append(String.format(BY_PHONE, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "age":
                    queryBuilder.append(String.format(BY_AGE, DESC.equalsIgnoreCase(direction) ? ASC : DESC));
                    break;
                default:
                    queryBuilder.append(String.format(BY_NAME, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
            }
        }
        return queryBuilder.toString();
    }

    public SearchResumeResponseDto getResponse(SearchRequestDto searchRequestDto) {
        SearchResumeResponseDto searchResumeResponseDTO = SearchResumeResponseDto.builder()
                .count(getCount(getQuery(true, searchRequestDto.getSearchParameter(),
                        searchRequestDto.getSearchSort(), searchRequestDto.getDirection())
                        , searchRequestDto.getSearchText()))
                .searchResumeDtos(getResult(getQuery(false,
                        searchRequestDto.getSearchParameter(), searchRequestDto.getSearchSort()
                        , searchRequestDto.getDirection())
                        , searchRequestDto.getSearchText()
                        , searchRequestDto.getResultsOnPage()
                        , searchRequestDto.getFirstResultNumber()))
                .build();
        log.info("Resume response = " + searchResumeResponseDTO.toString());
        return searchResumeResponseDTO;
    }

}
