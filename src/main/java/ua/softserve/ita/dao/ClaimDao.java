package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Claim;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("claimDao")
@Repository
public class ClaimDao implements Dao<Claim> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Claim findById(Long id) {
        return sessionFactory.getCurrentSession().get(Claim.class, id);
    }

    @Override
    public List<Claim> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Claim> criteriaQuery = criteriaBuilder.createQuery(Claim.class);
        Root<Claim> root = criteriaQuery.from(Claim.class);
        criteriaQuery.select(root);
        Query<Claim> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Claim create(Claim claim) {
        sessionFactory.getCurrentSession().save(claim);

        return claim;
    }

    @Override
    public Claim update(Claim claim) {
        Session session = sessionFactory.getCurrentSession();

        session.update(claim);
        session.flush();

        return claim;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Claim claim = session.byId(Claim.class).load(id);
        session.delete(claim);
    }
}
