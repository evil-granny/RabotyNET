package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchResumeResponseDTO;
import ua.softserve.ita.dto.SearchDTO.SearchVacancyResponseDTO;
import ua.softserve.ita.service.search.SearchResumeService;
import ua.softserve.ita.service.search.SearchVacancyService;

@RestController
@Slf4j
public class SearchController {

    private final SearchResumeService searchResumeService;
    private final SearchVacancyService searchVacancyService;

    @Autowired
    public SearchController(SearchResumeService searchResumeService, SearchVacancyService searchVacancyService) {
        this.searchResumeService = searchResumeService;
        this.searchVacancyService = searchVacancyService;
    }

    @PostMapping("/search/resume")
    public SearchResumeResponseDTO getResult(@RequestBody SearchRequestDTO searchRequestDTO) {
        log.info("Request = " + searchRequestDTO.toString());
        return searchResumeService.getResponse(searchRequestDTO);

    }

    @PostMapping("/search/vacancies")
    public SearchVacancyResponseDTO getVacanciesResult(@RequestBody SearchRequestDTO searchRequestDTO) {
        log.info("Request = " + searchRequestDTO.toString());
        return searchVacancyService.getResponse(searchRequestDTO);
    }

}
