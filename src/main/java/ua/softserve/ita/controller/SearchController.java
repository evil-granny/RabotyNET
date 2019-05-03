package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.model.SearchCV;
import ua.softserve.ita.service.SearchCVService;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
public class SearchController {
    Logger LOGGER = Logger.getLogger("ua.softserve.ita.controller.searchController");
    @Resource(name = "searchCVService")
    private SearchCVService searchCVService;

//    @PostMapping("/searchCV")
//    public List<Person> getResult(@RequestBody SearchCV searchCV) {
//        LOGGER.severe("Request = " + searchCV.toString());
//        return searchCVService.getShortCvs(searchCV.searchParameter, searchCV.searchText.trim());
//    }

}
