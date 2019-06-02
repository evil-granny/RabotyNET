package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.RequirementDao;
import ua.softserve.ita.model.Requirement;

@Repository
public class RequirementDaoImpl extends AbstractDao<Requirement, Long> implements RequirementDao {
}
