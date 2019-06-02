package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.dto.search.SearchRequestDto;
import ua.softserve.ita.dto.search.SearchResumeResponseDto;
import ua.softserve.ita.dto.search.SearchVacancyResponseDto;
import ua.softserve.ita.service.impl.search.SearchResumeService;
import ua.softserve.ita.service.impl.search.SearchVacancyService;

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

    @PostMapping("/searchResume")
    public SearchResumeResponseDto getResult(@RequestBody SearchRequestDto searchRequestDto) {
        log.info("Request = " + searchRequestDto.toString());
        return searchResumeService.getResponse(searchRequestDto);

    }

    @PostMapping("/searchVacancy")
    public SearchVacancyResponseDto getVacanciesResult(@RequestBody SearchRequestDto searchRequestDto) {
        log.info("Request = " + searchRequestDto.toString());
        return searchVacancyService.getResponse(searchRequestDto);
    }

}
