package ua.softserve.ita.dao.impl.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchVacancyDto;
import ua.softserve.ita.dto.search.SearchVacancyResponseDto;
import ua.softserve.ita.service.search.SearchVacancyMapper;

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

    private List<SearchVacancyDto> getSearchVacancyDTOS(List<Object> list) {
        List<SearchVacancyDto> dtoList = new ArrayList<>();
        SearchVacancyMapper searchVacancyMapper = new SearchVacancyMapper();
        SearchVacancyDto searchVacancyDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object object : list) {
            try {
                searchVacancyDTO = searchVacancyMapper.getSearchVacancyDto(objectMapper.writeValueAsString(object));
                log.info("DTO = " + searchVacancyDTO);
                dtoList.add(searchVacancyDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return dtoList;
    }

    @SuppressWarnings("unchecked")
    private List<Object> getResult(String query, String searchText,
                                   int resultsOnPage, int firstResultNumber) {
        return session.createNativeQuery(query)
                .setParameter(SEARCH_TEXT, "%" + searchText + "%")
                .setFirstResult(firstResultNumber)
                .setMaxResults(resultsOnPage)
                .getResultList();
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

    public SearchVacancyResponseDto getResponse(SearchRequestDto searchRequestDTO) {
        SearchVacancyResponseDto searchVacancyResponseDTO = SearchVacancyResponseDto.builder()
                .count(getCount(getQuery(true, searchRequestDTO.getSearchParameter(),
                        searchRequestDTO.getSearchSort(), searchRequestDTO.getDirection())
                        , searchRequestDTO.getSearchText()))
                .searchVacancyDtos(getSearchVacancyDTOS(getResult(getQuery(false,
                        searchRequestDTO.getSearchParameter(), searchRequestDTO.getSearchSort()
                        , searchRequestDTO.getDirection())
                        , searchRequestDTO.getSearchText(), searchRequestDTO.getResultsOnPage()
                        , searchRequestDTO.getFirstResultNumber())))
                .build();
        log.info("searchVacancyResponseDTO = " + searchVacancyResponseDTO.toString());
        return searchVacancyResponseDTO;

    }
}
