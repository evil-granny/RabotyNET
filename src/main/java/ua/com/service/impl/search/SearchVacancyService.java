package ua.com.service.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.dao.impl.search.SearchVacancyDao;
import ua.com.dto.search.SearchRequestDto;
import ua.com.dto.search.SearchVacancyResponseDto;
import ua.com.service.SearchService;


@Component
@Slf4j
public class SearchVacancyService implements SearchService<SearchVacancyResponseDto> {

    private final SearchVacancyDao searchVacancyDao;

    @Autowired
    public SearchVacancyService(SearchVacancyDao searchVacancyDao) {
        this.searchVacancyDao = searchVacancyDao;
    }

    @Override
    public SearchVacancyResponseDto getResponse(SearchRequestDto searchRequestDto) {
        return searchVacancyDao.getResponse(searchRequestDto);
    }

}
