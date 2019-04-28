package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.model.SearchCV;
import ua.softserve.ita.service.SearchCVService;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class SearchController {

    @Resource(name = "searchCVService")
    private SearchCVService searchCVService;

    @PostMapping("/searchCV")
    public List<Person> getResult(@RequestBody SearchCV searchCV) {
        return searchCVService.getShortCvs(searchCV.searchParameter, searchCV.searchText.trim());
    }

}
