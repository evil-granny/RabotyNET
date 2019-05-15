package ua.softserve.ita.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchVacancyDao;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;

@Component
public class SearchVacancyService {

    private final SearchVacancyDao searchVacancyDao;

    @Autowired
    public SearchVacancyService(SearchVacancyDao searchVacancyDao) {
        this.searchVacancyDao = searchVacancyDao;
    }

    public SearchVacancyResponseDTO getResponse(String searchParameter, String searchText, int resultsOnPage, int firstResultNumber) {
        return searchVacancyDao.search(searchParameter, searchText, resultsOnPage, firstResultNumber);

    }
}
