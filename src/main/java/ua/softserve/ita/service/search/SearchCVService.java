package ua.softserve.ita.service.search;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;
        import ua.softserve.ita.dao.impl.search.SearchCVDao;
        import ua.softserve.ita.model.profile.Person;

        import java.util.List;

@Component
public class SearchCVService {

    private final SearchCVDao searchCVDao;

    @Autowired
    public SearchCVService(SearchCVDao searchCVDao) {
        this.searchCVDao = searchCVDao;
    }

    public List<Person> getShortCvs(String searchParameter, String searchText, int resultsOnPage, int firstResultNumber) {
        return searchCVDao.search(searchParameter, searchText, resultsOnPage, firstResultNumber);

    }
}

