package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.JobDao;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.service.JobService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    private final JobDao jobDao;

    @Autowired
    public JobServiceImpl(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    @Override
    public Optional<Job> findById(Long id) {
        return jobDao.findById(id);
    }

    @Override
    public List<Job> findAll() {
        return jobDao.findAll();
    }

    @Override
    public Job save(Job job) {
        return jobDao.save(job);
    }

    @Override
    public Job update(Job job) {
        return jobDao.update(job);
    }

    @Override
    public void deleteById(Long id) {
        jobDao.deleteById(id);
    }

}
