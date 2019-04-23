package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Requirement;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Component("requirementDao")
@Repository
public class RequirementDao implements Dao<Requirement> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Requirement findById(Long id) {
        Requirement requirement = sessionFactory.getCurrentSession().get(Requirement.class, id);
        if (requirement == null) {
            try {
                throw new ResourceNotFoundException("Requirement not found for this id: " + id);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        return requirement;
    }

    @Override
    public List<Requirement> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Requirement> criteriaQuery = criteriaBuilder.createQuery(Requirement.class);
        Root<Requirement> root = criteriaQuery.from(Requirement.class);
        criteriaQuery.select(root);
        Query<Requirement> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Requirement create(Requirement requirement) {
        sessionFactory.getCurrentSession().save(requirement);
        return requirement;
    }

    @Override
    public Requirement update(Requirement requirement) {
        Session session = sessionFactory.getCurrentSession();
        session.update(requirement);
        session.flush();
        return requirement;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Requirement requirement = session.byId(Requirement.class).load(id);
        if (requirement == null) {
            try {
                throw new ResourceNotFoundException("Requirement not found for this id: " + id);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        session.delete(requirement);
    }
}
