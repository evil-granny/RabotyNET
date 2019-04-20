package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Vacancy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Component("vacancyDao")
@Repository
public class VacancyDao implements BaseDao<Vacancy> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Vacancy findById(Long id) {
        Vacancy vacancy = sessionFactory.getCurrentSession().get(Vacancy.class, id);
        if (vacancy == null) {
            try {
                throw new ResourceNotFoundException("Vacancy not found for this id: " + id);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        return vacancy;
    }

    @Override
    public List<Vacancy> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Vacancy> criteriaQuery = criteriaBuilder.createQuery(Vacancy.class);
        Root<Vacancy> root = criteriaQuery.from(Vacancy.class);
        criteriaQuery.select(root);
        Query<Vacancy> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Vacancy insert(Vacancy vacancy) {
        sessionFactory.getCurrentSession().save(vacancy);
        return vacancy;
    }

    @Override
    public Vacancy update(Vacancy vacancy, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Vacancy updatedVacancy = sessionFactory.getCurrentSession().byId(Vacancy.class).load(id);
        if (updatedVacancy == null) {
            try {
                throw new ResourceNotFoundException("Vacancy not found for this id: " + id);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        Objects.requireNonNull(updatedVacancy).setPosition(vacancy.getPosition());
        updatedVacancy.setSalary(vacancy.getSalary());
        updatedVacancy.setEmployment(vacancy.getEmployment());
        updatedVacancy.setRequirements(vacancy.getRequirements());
        updatedVacancy.setCompany(vacancy.getCompany());
        session.flush();
        return updatedVacancy;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Vacancy vacancy = session.byId(Vacancy.class).load(id);
        if (vacancy == null) {
            try {
                throw new ResourceNotFoundException("Vacancy not found for this id: " + id);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        }
        session.delete(vacancy);
    }
}
