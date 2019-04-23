package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Vacancy;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("vacancyDao")
@Repository
public class VacancyDao implements Dao<Vacancy> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Vacancy findById(Long id) {
        return sessionFactory.getCurrentSession().get(Vacancy.class, id);
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
        updatedVacancy.setPosition(updatedVacancy.getPosition());
        updatedVacancy.setSalary(updatedVacancy.getSalary());
        updatedVacancy.setTypeOfEmployment(updatedVacancy.getTypeOfEmployment());
        updatedVacancy.setRequirements(updatedVacancy.getRequirements());
        session.flush();

        return vacancy;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Vacancy vacancy = session.byId(Vacancy.class).load(id);
        session.delete(vacancy);
    }
}
