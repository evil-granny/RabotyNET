package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.SkillDao;
import ua.softserve.ita.model.Skill;

@Repository
public class SkillDaoImpl extends AbstractDao<Skill, Long> implements SkillDao {
}
