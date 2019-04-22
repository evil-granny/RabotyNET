package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Education;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("educationDao")
@Repository
public class EducationDao implements Dao<Education> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Education findById(Long id) {
        return sessionFactory.getCurrentSession().get(Education.class, id);
    }

    @Override
    public List<Education> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Education> criteriaQuery = criteriaBuilder.createQuery(Education.class);
        Root<Education> root = criteriaQuery.from(Education.class);
        criteriaQuery.select(root);
        Query<Education> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Education insert(Education education) {
        sessionFactory.getCurrentSession().save(education);
        return education;
    }

    @Override
    public Education update(Education education) {
        Session session = sessionFactory.getCurrentSession();
        session.update(education);
        session.flush();

        return education;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Education education = session.byId(Education.class).load(id);
        session.delete(education);
    }
}
