package ua.softserve.ita.dao.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchVacancyDto;
import ua.softserve.ita.dto.search.SearchVacancyResponseDto;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SearchVacancyDao {

    private static final String SELECT =
            "SELECT DISTINCT vacancy.position, vacancy.salary, vacancy.employment, vacancy.vacancy_id, " +
                    "vacancy.company_id, company.name, address.city, vacancy.currency " +
                    "FROM vacancy";
    private static final String JOIN_COMPANY = " JOIN company ON vacancy.company_id = company.company_id";
    private static final String JOIN_ADDRESS = " JOIN address ON vacancy.company_id = address.address_id";
    private static final String STATUS = " WHERE company.status = 'APPROVED' and" +
            " vacancy.vacancy_status = 'OPEN' and";
    private static final String POSITION = " vacancy.position ILIKE :searchText";
    private static final String CITY = " address.city ILIKE :searchText";
    private static final String COMPANY = " company.name ILIKE :searchText";
    private static final String SELECT_COUNT = "SELECT DISTINCT COUNT(vacancy.vacancy_id) " +
            "FROM vacancy";
    private static final String BY_POSITION = " ORDER BY vacancy.position %s, company.name, address.city";
    private static final String BY_CITY = " ORDER BY address.city %s, vacancy.position, company.name";
    private static final String BY_COMPANY = " ORDER BY company.name %s, vacancy.position, address.city";
    private static final String BY_EMPLOYMENT = " ORDER BY vacancy.employment %s, vacancy.position, company.name";
    private static final String BY_SALARY = " ORDER BY vacancy.salary %s, vacancy.position, company.name";
    private static final String DESC = "DESC";
    private static final String ASC = "ASC";

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

    private List<SearchVacancyDto> getResult(String query, String searchText,
                                             int resultsOnPage, int firstResultNumber) {
        List<Tuple> tupleList = session.createNativeQuery(query, Tuple.class)
                .setParameter(SEARCH_TEXT, "%" + searchText + "%")
                .setFirstResult(firstResultNumber)
                .setMaxResults(resultsOnPage)
                .getResultList();

        List<SearchVacancyDto> dtoList = new ArrayList<>();
        for (Tuple tuple : tupleList) {
            SearchVacancyDto searchVacancyDto = new SearchVacancyDto();
            searchVacancyDto.setVacancyId(tuple.get("vacancy_id", BigInteger.class));
            searchVacancyDto.setCompanyId(tuple.get("company_id", BigInteger.class));
            try {
                searchVacancyDto.setPosition(tuple.get("position", String.class));
            } catch (IllegalArgumentException e) {
                searchVacancyDto.setPosition("");
            }
            try {
                searchVacancyDto.setCompanyName(tuple.get("name", String.class));
            } catch (IllegalArgumentException e) {
                searchVacancyDto.setCompanyName("");
            }
            try {
                searchVacancyDto.setCity(tuple.get("city", String.class));
            } catch (IllegalArgumentException e) {
                searchVacancyDto.setCity("");
            }
            try {
                searchVacancyDto.setEmployment(tuple.get("employment", String.class));
            } catch (IllegalArgumentException e) {
                searchVacancyDto.setEmployment("");
            }
            try {
                searchVacancyDto.setSalary(tuple.get("salary", Integer.class));
            } catch (IllegalArgumentException e) {
                searchVacancyDto.setSalary(0);
            }
            try {
                searchVacancyDto.setCurrency(tuple.get("currency", String.class));
            } catch (IllegalArgumentException e) {
                searchVacancyDto.setCurrency("");
            }
            dtoList.add(searchVacancyDto);
        }
        return dtoList;
    }

    private String getQuery(Boolean isCount, String searchParameter, String searchSort, String direction) {
        StringBuilder queryBuilder = new StringBuilder();

        if (isCount) {
            queryBuilder.append(SELECT_COUNT).append(JOIN_COMPANY);
        } else {
            queryBuilder.append(SELECT).append(JOIN_COMPANY).append(JOIN_ADDRESS).append(STATUS);
        }

        switch (searchParameter) {
            case "city":
                if (isCount) {
                    queryBuilder.append(JOIN_ADDRESS).append(STATUS);
                }
                queryBuilder.append(CITY);
                break;
            case "company":
                if (isCount) {
                    queryBuilder.append(JOIN_ADDRESS).append(STATUS);
                }
                queryBuilder.append(COMPANY);
                break;
            default:
                if (isCount) {
                    queryBuilder.append(STATUS);
                }
                queryBuilder.append(POSITION);
        }

        if (!isCount) {
            switch (searchSort) {
                case "city":
                    queryBuilder.append(String.format(BY_CITY, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "position":
                    queryBuilder.append(String.format(BY_POSITION, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "employment":
                    queryBuilder.append(String.format(BY_EMPLOYMENT, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                case "salary":
                    queryBuilder.append(String.format(BY_SALARY, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
                    break;
                default:
                    queryBuilder.append(String.format(BY_COMPANY, DESC.equalsIgnoreCase(direction) ? DESC : ASC));
            }
        }
        log.info("Query = " + queryBuilder);
        return queryBuilder.toString();
    }

    public SearchVacancyResponseDto getResponse(SearchRequestDto searchRequestDto) {
        SearchVacancyResponseDto searchVacancyResponseDto = SearchVacancyResponseDto.builder()
                .count(getCount(getQuery(true, searchRequestDto.getSearchParameter(),
                        searchRequestDto.getSearchSort(), searchRequestDto.getDirection())
                        , searchRequestDto.getSearchText()))
                .searchVacancyDtos(getResult(getQuery(false,
                        searchRequestDto.getSearchParameter(), searchRequestDto.getSearchSort()
                        , searchRequestDto.getDirection())
                        , searchRequestDto.getSearchText(), searchRequestDto.getResultsOnPage()
                        , searchRequestDto.getFirstResultNumber()))
                .build();
        log.info("Vacancy response = " + searchVacancyResponseDto.toString());
        return searchVacancyResponseDto;
    }
}
