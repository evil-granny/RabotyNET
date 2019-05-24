package ua.softserve.ita.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchResumeDao;
import ua.softserve.ita.dto.SearchDTO.SearchResumeResponseDTO;

@Component
public class SearchResumeService {

    private final SearchResumeDao searchResumeDao;

    @Autowired
    public SearchResumeService(SearchResumeDao searchResumeDao) {
        this.searchResumeDao = searchResumeDao;
    }

    public SearchResumeResponseDTO getResponse(String searchParameter, String searchText, int resultsOnPage, int firstResultNumber) {
        return searchResumeDao.search(searchParameter, searchText, resultsOnPage, firstResultNumber);

    }
}

