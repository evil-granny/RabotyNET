package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SearchVacancyDao {

    private static final String SELECT =
            "SELECT DISTINCT vacancy.position, vacancy.salary, vacancy.employment, vacancy.vacancy_id, " +
                    "vacancy.company_id, company.name, address.city " +
                    "FROM vacancy";
    private static final String JOIN_COMPANY = " JOIN company ON vacancy.company_id = company.company_id";
    private static final String JOIN_ADDRESS = " JOIN address ON vacancy.company_id = address.address_id";
    private static final String POSITION = " WHERE vacancy.position ILIKE :searchText";
    private static final String CITY = " WHERE address.city ILIKE :searchText";
    private static final String COMPANY = " WHERE company.name ILIKE :searchText";
    private static final String SELECT_COUNT = "SELECT DISTINCT COUNT(vacancy.vacancy_id) " +
            "FROM vacancy";
    private static final String BY_POSITION = " ORDER BY vacancy.position";
    private static final String BY_CITY = " ORDER BY address.city";
    private static final String BY_COMPANY = " ORDER BY company.name";
    private static final String BY_EMPLOYMENT = " ORDER BY vacancy.employment";
    private static final String BY_SALARY = " ORDER BY vacancy.salary";
    private static final String DIRECTION = " DESC";

    private static final String SEARCH_TEXT = "searchText";

    private Session session;

    @Autowired
    public SearchVacancyDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    private BigInteger getCount(String query, String searchText) {
        return (BigInteger) session.createNativeQuery(query)
                .setParameter(SEARCH_TEXT, "%" + searchText + "%").getSingleResult();
    }

    private List<SearchVacancyDTO> getResult(String query, String searchText,
                                             int resultsOnPage, int firstResultNumber) {
        List<Tuple> tupleList = session.createNativeQuery(query, Tuple.class)
                .setParameter(SEARCH_TEXT, "%" + searchText + "%")
                .setFirstResult(firstResultNumber)
                .setMaxResults(resultsOnPage)
                .getResultList();

        List<SearchVacancyDTO> dtoList = new ArrayList<>();
        for (Tuple tuple : tupleList) {
            dtoList.add(SearchVacancyDTO.builder()
                    .vacancyId(tuple.get("vacancy_id", BigInteger.class))
                    .companyId(tuple.get("company_id", BigInteger.class))
                    .position(tuple.get("position", String.class))
                    .companyName(tuple.get("name", String.class))
                    .city(tuple.get("city", String.class))
                    .employment(tuple.get("employment", String.class))
                    .salary(tuple.get("salary", Integer.class))
                    .build());
        }
        return dtoList;
    }

    private String getQuery(Boolean isCount, String searchParameter, String searchSort, String direction) {
        StringBuilder queryBuilder = new StringBuilder();

        if (isCount) {
            queryBuilder.append(SELECT_COUNT);
        } else {
            queryBuilder.append(SELECT).append(JOIN_COMPANY).append(JOIN_ADDRESS);
        }

        switch (searchParameter) {
            case "city":
                if (isCount) {
                    queryBuilder.append(JOIN_COMPANY).append(JOIN_ADDRESS);
                }
                queryBuilder.append(CITY);
                break;
            case "company":
                if (isCount) {
                    queryBuilder.append(JOIN_COMPANY).append(JOIN_ADDRESS);
                }
                queryBuilder.append(COMPANY);
                break;
            default:
                queryBuilder.append(POSITION);
        }

        if (!isCount) {
            switch (searchSort) {
                case "city":
                    queryBuilder.append(BY_CITY);
                    if ("desc".equals(direction)) {
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "position":
                    queryBuilder.append(BY_POSITION);
                    if ("desc".equals(direction)) {
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "employment":
                    queryBuilder.append(BY_EMPLOYMENT);
                    if ("desc".equals(direction)) {
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                case "salary":
                    queryBuilder.append(BY_SALARY);
                    if ("desc".equals(direction)) {
                        queryBuilder.append(DIRECTION);
                    }
                    break;
                default:
                    queryBuilder.append(BY_COMPANY);
                    if ("desc".equals(direction)) {
                        queryBuilder.append(DIRECTION);
                    }
            }
        }
        log.info("query = " + queryBuilder.toString());
        return queryBuilder.toString();
    }

    public SearchVacancyResponseDTO getResponse(SearchRequestDTO searchRequestDTO) {
        return SearchVacancyResponseDTO.builder()
                .count(getCount(getQuery(true, searchRequestDTO.getSearchParameter(),
                        searchRequestDTO.getSearchSort(), searchRequestDTO.getDirection())
                        , searchRequestDTO.getSearchText()))
                .searchVacancyDTOS(getResult(getQuery(false,
                        searchRequestDTO.getSearchParameter(), searchRequestDTO.getSearchSort()
                        , searchRequestDTO.getDirection())
                        , searchRequestDTO.getSearchText(), searchRequestDTO.getResultsOnPage()
                        , searchRequestDTO.getFirstResultNumber()))
                .build();
    }
}
