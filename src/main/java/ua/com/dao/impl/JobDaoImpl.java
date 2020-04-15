package ua.com.dao.impl;

import org.springframework.stereotype.Repository;
import ua.com.dao.JobDao;
import ua.com.model.Job;

@Repository
public class JobDaoImpl extends AbstractDao<Job, Long> implements JobDao {
}
