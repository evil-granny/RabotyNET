package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchCVResponseDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;
import ua.softserve.ita.service.search.SearchCVService;
import ua.softserve.ita.service.search.SearchVacancyService;

@CrossOrigin
@RestController
@Slf4j
public class SearchController {

    private final SearchCVService searchCVService;
    private final SearchVacancyService searchVacancyService;

    @Autowired
    public SearchController(SearchCVService searchCVService, SearchVacancyService searchVacancyService) {
        this.searchCVService = searchCVService;
        this.searchVacancyService = searchVacancyService;
    }

    @PostMapping("/searchCV")
    public SearchCVResponseDTO getResult(@RequestBody SearchRequestDTO searchRequestDTO) {
        log.info("Request = " + searchRequestDTO.toString());

        return searchCVService.getResponse(searchRequestDTO.getSearchParameter(), searchRequestDTO.getSearchText().trim(),
                searchRequestDTO.getResultsOnPage(), searchRequestDTO.getFirstResultNumber());

    }

    @PostMapping("/vacancies/search")
    public SearchVacancyResponseDTO getVacanciesResult(@RequestBody SearchRequestDTO searchRequestDTO) {
        log.info("Request = " + searchRequestDTO.toString());

        return searchVacancyService.getResponse(searchRequestDTO.getSearchParameter(), searchRequestDTO.getSearchText().trim(),
                searchRequestDTO.getResultsOnPage(), searchRequestDTO.getFirstResultNumber());
    }

}
