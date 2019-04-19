package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.CV;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("cvDao")
@Repository
public class CVDao implements Dao<CV> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public CV findById(Long id) {
        return sessionFactory.getCurrentSession().get(CV.class, id);
    }

    @Override
    public List<CV> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CV> criteriaQuery = criteriaBuilder.createQuery(CV.class);
        Root<CV> root = criteriaQuery.from(CV.class);
        criteriaQuery.select(root);
        Query<CV> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public CV create(CV cv) {
        sessionFactory.getCurrentSession().save(cv);

        return cv;
    }

    @Override
    public CV update(CV cv, Long id) {
        Session session = sessionFactory.getCurrentSession();

        CV updatedCV = sessionFactory.getCurrentSession().byId(CV.class).load(id);
        updatedCV.setEducation(cv.getEducation());
        updatedCV.setJobs(cv.getJobs());
        updatedCV.setPhoto(cv.getPhoto());
        updatedCV.setPosition(cv.getPosition());
        updatedCV.setSkills(cv.getSkills());

        session.flush();

        return updatedCV;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        CV cv = session.byId(CV.class).load(id);
        session.delete(cv);
    }

}
