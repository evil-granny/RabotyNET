package ua.softserve.ita.service;

import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.RequirementDao;
import ua.softserve.ita.model.Requirement;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class RequirementService implements Service<Requirement> {

    @Resource(name = "requirementDao")
    private RequirementDao requirementDao;

    @Override
    public Requirement findById(Long id) {
        return requirementDao.findById(id);
    }

    @Override
    public List<Requirement> findAll() {
        return requirementDao.findAll();
    }

    @Override
    public Requirement create(Requirement requirement) {
        return requirementDao.create(requirement);
    }

    @Override
    public Requirement update(Requirement requirement) {
        return requirementDao.update(requirement);
    }

    @Override
    public void deleteById(Long id) {
        requirementDao.deleteById(id);
    }
}
