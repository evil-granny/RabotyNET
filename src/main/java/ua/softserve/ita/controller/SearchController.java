package ua.softserve.ita.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.model.Person;
import ua.softserve.ita.service.SearchCVService;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class SearchController {
    Logger LOGGER = Logger.getLogger("ua.softserve.ita.controller.searchController");

    @Resource(name = "searchCVService")
    private SearchCVService searchCVService;

    @GetMapping("/searchCV")
    public ResponseEntity<List<Person>> searchResult(@RequestBody String request){
        String[] str= request.split("-");
        String parameter = str[0];
        String searchText = str[1].trim();
        List<Person> people = searchCVService.getShortCvs(parameter, searchText);
        return ResponseEntity.ok().body(people);
    }

}