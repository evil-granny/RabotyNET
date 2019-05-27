package ua.softserve.ita.service.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchDao;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SearchVacancyService implements SearchService<SearchVacancyResponseDTO> {

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

    private final SearchDao searchDao;

    @Autowired
    public SearchVacancyService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    private BigInteger getCount(String query, String searchText) {
        return searchDao.getCount(query, searchText);
    }

    private List<SearchVacancyDTO> getSearchVacancyDTOS(List<Object> list) {
        List<SearchVacancyDTO> dtoList = new ArrayList<>();
        SearchVacancyMapper searchVacancyMapper = new SearchVacancyMapper();
        SearchVacancyDTO searchVacancyDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object object : list) {
            try {
                searchVacancyDTO = searchVacancyMapper.getSearchVacancyDTO(objectMapper.writeValueAsString(object));
                log.info("DTO = " + searchVacancyDTO);
                dtoList.add(searchVacancyDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return dtoList;
    }

    private List<Object> getResult(String query, String searchText, int resultsOnPage, int firstResultNumber) {
        return searchDao.getResult(query, searchText, resultsOnPage, firstResultNumber);
    }

    private String getQuery(Boolean isCount, String searchParameter, String searchSort) {
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
                    break;
                case "position":
                    queryBuilder.append(BY_POSITION);
                    break;
                default:
                    queryBuilder.append(BY_COMPANY);
            }
        }
        log.info("query = " + queryBuilder.toString());
        return queryBuilder.toString();
    }

    @Override
    public SearchVacancyResponseDTO getResponse(SearchRequestDTO searchRequestDTO) {
        SearchVacancyResponseDTO searchVacancyResponseDTO = SearchVacancyResponseDTO.builder()
                .count(getCount(getQuery(true, searchRequestDTO.getSearchParameter(),
                        searchRequestDTO.getSearchSort()), searchRequestDTO.getSearchText()))
                .searchVacancyDTOS(getSearchVacancyDTOS(getResult(getQuery(false,
                        searchRequestDTO.getSearchParameter(), searchRequestDTO.getSearchSort())
                        , searchRequestDTO.getSearchText(), searchRequestDTO.getResultsOnPage()
                        , searchRequestDTO.getFirstResultNumber())))
                .build();
        log.info("searchVacancyResponseDTO = " + searchVacancyResponseDTO.toString());
        return searchVacancyResponseDTO;

    }
}
