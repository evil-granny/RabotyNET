package ua.com.service.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.dao.impl.search.SearchResumeDao;
import ua.com.dto.search.SearchRequestDto;
import ua.com.dto.search.SearchResumeResponseDto;
import ua.com.service.SearchService;

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

