package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.BaseDao;
import ua.softserve.ita.model.Vacancy;

import javax.annotation.Resource;
import java.util.List;

@Component("vacancyService")
@Service
@Transactional
public class VacancyService implements IService<Vacancy> {

    @Resource(name = "vacancyDao")
    private BaseDao<Vacancy> vacancyDao;

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
