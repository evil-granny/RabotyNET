package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.model.SearchCV;
import ua.softserve.ita.service.SearchCVService;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class SearchController {
    @Resource(name = "searchCVService")
    private SearchCVService searchCVService;

    @PostMapping("/searchCV")
    public List<Person> getResult(@RequestBody SearchCV searchCV) {

        log.info("Request = " + searchCV.toString());
        return searchCVService.getShortCvs(searchCV.getSearchParameter(), searchCV.getSearchText().trim());
    }
}
