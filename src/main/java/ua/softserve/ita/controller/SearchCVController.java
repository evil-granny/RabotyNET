package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.dto.SearchDTO;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.search.SearchCVService;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class SearchCVController {

    private final SearchCVService searchCVService;

    @Autowired
    public SearchCVController(SearchCVService searchCVService) {
        this.searchCVService = searchCVService;
    }

    @PostMapping("/searchCV")
    public List<Person> getResult(@RequestBody SearchDTO searchDTO) {
        log.info("Request = " + searchDTO.toString());
        return searchCVService.getShortCvs(searchDTO.getSearchParameter(), searchDTO.getSearchText().trim(),
                searchDTO.getResultsOnPage(), searchDTO.getFirstResultNumber());
    }
}
