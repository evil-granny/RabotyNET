package ua.softserve.ita.service.impl.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchVacancyDao;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchVacancyResponseDto;
import ua.softserve.ita.service.SearchService;


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
