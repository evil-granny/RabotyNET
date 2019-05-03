package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Status;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("statusDao")
@Repository
public class StatusDao implements Dao<Status>{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Status findById(Long id) {
        return sessionFactory.getCurrentSession().get(Status.class, id);
    }

    @Override
    public List<Status> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Status> criteriaQuery = criteriaBuilder.createQuery(Status.class);
        Root<Status> root = criteriaQuery.from(Status.class);
        criteriaQuery.select(root);
        Query<Status> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Status create(Status status) {
        sessionFactory.getCurrentSession().save(status);

        return status;
    }

    @Override
    public Status update(Status status) {
        Session session = sessionFactory.getCurrentSession();

        session.update(status);
        session.flush();

        return status;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Status status = session.byId(Status.class).load(id);
        session.delete(status);
    }

}
