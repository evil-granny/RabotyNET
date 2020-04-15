package ua.com.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.model.Requirement;
import ua.com.dao.RequirementDao;

@Repository
public class RequirementDaoImpl extends AbstractDao<Requirement, Long> implements RequirementDao {
}
