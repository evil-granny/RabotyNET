package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.EducationDao;
import ua.softserve.ita.model.Education;

@Repository
public class EducationDaoImpl extends AbstractDao<Education, Long> implements EducationDao {
}
