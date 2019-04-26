package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import ua.softserve.ita.dao.SearchCVDao;
import ua.softserve.ita.model.Person;

import javax.annotation.Resource;
import java.util.List;

@Component("searchCVService")
public class SearchCVService {

    @Resource(name = "searchCVDao")
    private SearchCVDao searchCVDao;

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
