package ua.softserve.ita.service.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchVacancyDao;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;


@Component
@Slf4j
public class SearchVacancyService implements SearchService<SearchVacancyResponseDTO> {

    private final SearchVacancyDao searchVacancyDao;

    @Autowired
    public SearchVacancyService(SearchVacancyDao searchVacancyDao) {
        this.searchVacancyDao = searchVacancyDao;
    }

    @Override
    public SearchVacancyResponseDTO getResponse(SearchRequestDTO searchRequestDTO) {
        return searchVacancyDao.getResponse(searchRequestDTO);
    }
}
