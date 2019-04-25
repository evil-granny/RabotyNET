package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.Vacancy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class VacancyDao implements Dao<Vacancy> {

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
    public Vacancy create(Vacancy vacancy) {
        sessionFactory.getCurrentSession().save(vacancy);
        return vacancy;
    }

    @Override
    public Vacancy update(Vacancy vacancy) {
        Session session = sessionFactory.getCurrentSession();
        Company company = new Company();
        company.setCompanyId(1L);
        vacancy.setCompany(company);


        Set<Requirement> requirements = vacancy.getRequirements();
        requirements.forEach(e -> e.setVacancy(vacancy));
        requirements.forEach(session::update);

        session.update(vacancy);
        session.flush();

        return vacancy;
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
