package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Job;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("jobDao")
@Repository
public class JobDao implements Dao<Job> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Job findById(Long id) {
        return sessionFactory.getCurrentSession().get(Job.class, id);
    }

    @Override
    public List<Job> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Job> criteriaQuery = criteriaBuilder.createQuery(Job.class);
        Root<Job> root = criteriaQuery.from(Job.class);
        criteriaQuery.select(root);
        Query<Job> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Long insert(Job job) {
        sessionFactory.getCurrentSession().save(job);
        return job.getJobId();
    }

    @Override
    public Long update(Job job, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Job updatedJob = sessionFactory.getCurrentSession().byId(Job.class).load(id);
        updatedJob.setJobId(job.getJobId());
        updatedJob.setDescription(job.getDescription());
        updatedJob.setPeriod(job.getPeriod());
        updatedJob.setPosition(job.getPosition());
        session.flush();

        return id;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Job job = session.byId(Job.class).load(id);
        session.delete(job);
    }
}
