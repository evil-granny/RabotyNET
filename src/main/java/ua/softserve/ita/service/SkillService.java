package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Skill;

import javax.annotation.Resource;
import java.util.List;

@Component("skillService")
@org.springframework.stereotype.Service
@Transactional
public class SkillService implements Service<Skill> {

    @Resource(name = "skillDao")
    private Dao<Skill> skillDao;

    @Override
    public Skill findById(Long id) {
        return skillDao.findById(id);
    }

    @Override
    public List<Skill> findAll() {
        return skillDao.findAll();
    }

    @Override
    public Skill create(Skill skill) {
        return skillDao.create(skill);
    }

    @Override
    public Skill update(Skill skill) {
        return skillDao.update(skill);
    }

    @Override
    public void deleteById(Long id) {
        skillDao.deleteById(id);
    }

}
