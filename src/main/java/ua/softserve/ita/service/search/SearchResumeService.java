package ua.softserve.ita.service.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchResumeDao;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchResumeResponseDTO;

@Component
@Slf4j
public class SearchResumeService implements SearchService<SearchResumeResponseDTO> {

    private final SearchResumeDao searchResumeDao;

    @Autowired
    public SearchResumeService(SearchResumeDao searchResumeDao) {
        this.searchResumeDao = searchResumeDao;
    }

    @Override
    public SearchResumeResponseDTO getResponse(SearchRequestDTO searchRequestDTO) {
        return searchResumeDao.getResponse(searchRequestDTO);
    }

}

