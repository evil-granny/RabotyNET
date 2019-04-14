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
    public Long insert(Skill skill) {
        return skillDao.insert(skill);
    }

    @Override
    public Long update(Skill skill, Long id) {
        return skillDao.update(skill, id);
    }

    @Override
    public void deleteById(Long id) {
        skillDao.deleteById(id);
    }
}
