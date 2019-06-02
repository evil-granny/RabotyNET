package ua.softserve.ita.service.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchResumeDao;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchResumeResponseDto;
import ua.softserve.ita.service.SearchService;

@Component
@Slf4j
public class SearchResumeService implements SearchService<SearchResumeResponseDto> {

    private final SearchResumeDao searchResumeDao;

    @Autowired
    public SearchResumeService(SearchResumeDao searchResumeDao) {
        this.searchResumeDao = searchResumeDao;
    }

    @Override
    public SearchResumeResponseDto getResponse(SearchRequestDto searchRequestDto) {
        return searchResumeDao.getResponse(searchRequestDto);
    }

}

