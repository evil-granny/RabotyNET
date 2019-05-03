package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RequirementDao implements Dao<Requirement> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Requirement findById(Long id) {
        return sessionFactory.getCurrentSession().get(Requirement.class, id);
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
        Query query = (Query) sessionFactory.createEntityManager().createNativeQuery
                ("SELECT * FROM vacancy WHERE vacancy_id = (SELECT requirement.vacancy_id FROM requirement WHERE requirement_id = :id)", Vacancy.class);
        query.setParameter("id", requirement.getRequirementId());
        Vacancy vacancy = (Vacancy) query.getSingleResult();
        requirement.setVacancy(vacancy);
        session.update(requirement);
        session.flush();
        return requirement;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Requirement requirement = session.byId(Requirement.class).load(id);
        if (requirement == null) {
            throw new ResourceNotFoundException("Requirement not found by id: " + id);
        }
        session.delete(requirement);
    }
}
