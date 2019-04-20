package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Education;

import javax.annotation.Resource;
import java.util.List;

@Component("educationService")
@org.springframework.stereotype.Service
@Transactional
public class EducationService implements Service<Education> {

    @Resource(name = "educationDao")
    private Dao<Education> educationDao;

    @Override
    public Education findById(Long id) {
        return educationDao.findById(id);
    }

    @Override
    public List<Education> findAll() {
        return educationDao.findAll();
    }

    @Override
    public Education insert(Education education) {
        return educationDao.insert(education);
    }

    @Override
    public Education update(Education education, Long id) {
        return educationDao.update(education, id);
    }

    @Override
    public void deleteById(Long id) {
        educationDao.deleteById(id);
    }
}
