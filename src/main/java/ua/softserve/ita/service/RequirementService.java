package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.BaseDao;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;

import javax.annotation.Resource;
import java.util.List;

@Component("requirementService")
@Service
@Transactional
public class RequirementService implements IService<Requirement> {

    @Resource(name = "requirementDao")
    private BaseDao<Requirement> requirementDao;

    @Override
    public Requirement findById(Long id) {
        return requirementDao.findById(id);
    }

    @Override
    public List<Requirement> findAll() {
        return requirementDao.findAll();
    }

    @Override
    public Requirement insert(Requirement requirement) {
        return requirementDao.insert(requirement);
    }

    @Override
    public Requirement update(Requirement requirement, Long id) {
        return requirementDao.update(requirement,id);
    }

    @Override
    public void deleteById(Long id) {
       requirementDao.deleteById(id);
    }
}
