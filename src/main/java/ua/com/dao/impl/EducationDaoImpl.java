package ua.com.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.dao.EducationDao;
import ua.com.model.Education;

@Repository
public class EducationDaoImpl extends AbstractDao<Education, Long> implements EducationDao {
}
