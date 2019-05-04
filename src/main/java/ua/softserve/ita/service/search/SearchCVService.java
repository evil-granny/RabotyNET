package ua.softserve.ita.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.impl.search.SearchCVDao;
import ua.softserve.ita.model.profile.Person;

import java.util.List;

@Component("searchCVService")
public class SearchCVService {

//    @Resource(name = "searchCVDao")
//    private SearchCVDao searchCVDao;
    private final SearchCVDao searchCVDao;

    @Autowired
    public SearchCVService(@Qualifier("searchCVDao") SearchCVDao searchCVDao) {
        this.searchCVDao = searchCVDao;
    }

    public List<Person> getShortCvs(String parameter, String searchText) {
        switch (parameter){
            case "name": return searchCVDao.searchByName(searchText);
            case "phoneNumber": return searchCVDao.searchByPhone(searchText);
            case "city": return searchCVDao.searchByCity(searchText);
            case "skill": return searchCVDao.searchBySkill(searchText);
            default: return searchCVDao.searchByAll(searchText);
        }

    }
}
