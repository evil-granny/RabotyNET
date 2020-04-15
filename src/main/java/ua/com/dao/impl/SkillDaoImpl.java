package ua.com.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.dao.SkillDao;
import ua.com.model.Skill;

@Repository
public class SkillDaoImpl extends AbstractDao<Skill, Long> implements SkillDao {
}
