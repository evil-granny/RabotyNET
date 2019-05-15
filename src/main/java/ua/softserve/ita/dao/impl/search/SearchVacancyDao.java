package ua.softserve.ita.dao.impl.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;
import ua.softserve.ita.service.search.SearchVacancyMapper;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SearchVacancyDao {

    private static final String POSITION_QUERY =
            "SELECT DISTINCT vacancy.position, vacancy.salary, vacancy.employment, vacancy.vacancy_id, " +
                    "vacancy.company_id, company.name, address.city " +
                    "FROM vacancy " +
                    "JOIN company ON vacancy.company_id = company.company_id " +
                    "JOIN address ON vacancy.company_id = address.address_id " +
                    "WHERE vacancy.position ILIKE :searchText ORDER BY vacancy.position";
    private static final String CITY_QUERY =
            "SELECT DISTINCT vacancy.position, vacancy.salary, vacancy.employment, vacancy.vacancy_id, " +
                    "vacancy.company_id, company.name, address.city " +
                    "FROM vacancy " +
                    "JOIN company ON vacancy.company_id = company.company_id " +
                    "JOIN address ON vacancy.company_id = address.address_id " +
                    "WHERE address.city ILIKE :searchText ORDER BY vacancy.position";
    private static final String COMPANY_QUERY =
            "SELECT DISTINCT vacancy.position, vacancy.salary, vacancy.employment, vacancy.vacancy_id, " +
                    "vacancy.company_id, company.name, address.city " +
                    "FROM vacancy " +
                    "JOIN company ON vacancy.company_id = company.company_id " +
                    "JOIN address ON vacancy.company_id = address.address_id " +
                    "WHERE company.name ILIKE :searchText ORDER BY vacancy.position";
    private static final String POSITION_QUERY_COUNT =
            "SELECT DISTINCT COUNT(vacancy.vacancy_id) " +
                    "FROM vacancy " +
                    "WHERE vacancy.position ILIKE :searchText";
    private static final String CITY_QUERY_COUNT =
            "SELECT DISTINCT COUNT(vacancy.vacancy_id) " +
                    "FROM vacancy " +
                    "JOIN company ON vacancy.company_id = company.company_id " +
                    "JOIN address ON vacancy.company_id = address.address_id " +
                    "WHERE address.city ILIKE :searchText";
    private static final String COMPANY_QUERY_COUNT =
            "SELECT DISTINCT COUNT(vacancy.vacancy_id) " +
                    "FROM vacancy " +
                    "JOIN company ON vacancy.company_id = company.company_id " +
                    "JOIN address ON vacancy.company_id = address.address_id " +
                    "WHERE company.name ILIKE :searchText";
    private Session session;

    @Autowired
    public SearchVacancyDao(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public SearchVacancyResponseDTO search(String searchParameter, String searchText,
                                           int resultsOnPage, int firstResultNumber) {
        SearchVacancyResponseDTO searchVacancyResponseDTO = new SearchVacancyResponseDTO();
        List<SearchVacancyDTO> dtoList = new ArrayList<>();
        String nativeQuery;
        String countQuery;
        switch (searchParameter) {
            case "city":
                nativeQuery = CITY_QUERY;
                countQuery = CITY_QUERY_COUNT;
                break;
            case "company":
                nativeQuery = COMPANY_QUERY;
                countQuery = COMPANY_QUERY_COUNT;
                break;
            default:
                nativeQuery = POSITION_QUERY;
                countQuery = POSITION_QUERY_COUNT;
        }
        Query getMathes = session.createNativeQuery(countQuery);
        getMathes.setParameter("searchText", "%" + searchText + "%");
        searchVacancyResponseDTO.setCount((BigInteger) getMathes.getSingleResult());
        log.info("Count = " + searchVacancyResponseDTO.getCount());

        Query query = session.createNativeQuery(nativeQuery);
        query.setParameter("searchText", "%" + searchText + "%");
        query.setFirstResult(firstResultNumber);
        query.setMaxResults(resultsOnPage);
        List result = query.getResultList();
        SearchVacancyMapper searchVacancyMapper= new SearchVacancyMapper();
        SearchVacancyDTO searchVacancyDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object object : result) {
            try {
                searchVacancyDTO = searchVacancyMapper.getSearchVacancyDTO(objectMapper.writeValueAsString(object));
                dtoList.add(searchVacancyDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        searchVacancyResponseDTO.setSearchVacancyDTOS(dtoList);
        for(SearchVacancyDTO searchVacancyDTO1 : searchVacancyResponseDTO.getSearchVacancyDTOS()) {
            log.info("Vacancy = " + searchVacancyDTO1);
        }

        return searchVacancyResponseDTO;
    }
}
