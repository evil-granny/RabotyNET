package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.SearchCVDao;
import ua.softserve.ita.model.Person;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Component("searchCVService")
public class SearchCVService {
    private final Logger LOGGER = Logger.getLogger("ua.softserve.ita.service.SearchCVService");

    @Resource(name = "searchCVDao")
    private SearchCVDao searchCVDao;

    public List<Person> getShortCvs(String parameter, String searchText) {
        return searchCVDao.searchByParameter(parameter, searchText);
    }
}