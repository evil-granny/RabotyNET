package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Vacancy;

import javax.annotation.Resource;
import java.util.List;

@Component("vacancyService")
@org.springframework.stereotype.Service
@Transactional
public class VacancyService implements Service<Vacancy> {

    @Resource(name = "vacancyDao")
    private Dao<Vacancy> vacancyDao;

    @Override
    public Vacancy findById(Long id) {
        return vacancyDao.findById(id);
    }

    @Override
    public List<Vacancy> findAll() {
        return vacancyDao.findAll();
    }

    @Override
    public Vacancy insert(Vacancy vacancy) {
        return vacancyDao.insert(vacancy);
    }

    @Override
    public Vacancy update(Vacancy vacancy, Long id) {
        return vacancyDao.update(vacancy, id);
    }

    @Override
    public void deleteById(Long id) {
        vacancyDao.deleteById(id);
    }
}
