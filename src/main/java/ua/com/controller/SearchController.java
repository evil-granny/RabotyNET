package ua.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.service.impl.search.SearchResumeService;
import ua.com.service.impl.search.SearchVacancyService;
import ua.com.dto.search.SearchRequestDto;
import ua.com.dto.search.SearchResumeResponseDto;
import ua.com.dto.search.SearchVacancyResponseDto;

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
