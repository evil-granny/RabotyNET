package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Job;

import javax.annotation.Resource;
import java.util.List;

@Component("jobService")
@org.springframework.stereotype.Service
@Transactional
public class JobService implements Service<Job> {

    @Resource(name = "jobDao")
    private Dao<Job> jobDao;

    @Override
    public Job findById(Long id) {
        return jobDao.findById(id);
    }

    @Override
    public List<Job> findAll() {
        return jobDao.findAll();
    }

    @Override
    public Job create(Job job) {
        return jobDao.create(job);
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
