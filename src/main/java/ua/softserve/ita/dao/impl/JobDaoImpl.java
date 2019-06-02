package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.JobDao;
import ua.softserve.ita.model.Job;

@Repository
public class JobDaoImpl extends AbstractDao<Job, Long> implements JobDao {
}
