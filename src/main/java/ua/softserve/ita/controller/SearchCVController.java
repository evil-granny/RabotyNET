package ua.softserve.ita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserve.ita.dto.SearchDTO.SearchCVRequestDTO;
import ua.softserve.ita.dto.SearchDTO.SearchCVResponseDTO;
import ua.softserve.ita.service.search.SearchCVService;

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
    public SearchCVResponseDTO getResult(@RequestBody SearchCVRequestDTO searchCVRequestDTO) {
        log.info("Request = " + searchCVRequestDTO.toString());
        return searchCVService.getResponse(searchCVRequestDTO.getSearchParameter(), searchCVRequestDTO.getSearchText().trim(),
                searchCVRequestDTO.getResultsOnPage(), searchCVRequestDTO.getFirstResultNumber());
    }
}
