package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Company;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("companyDao")
@Repository
public class CompanyDao implements Dao<Company> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Company findById(Long id) {
        return sessionFactory.getCurrentSession().get(Company.class, id);
    }

    @Override
    public List<Company> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);
        criteriaQuery.select(root);
        Query<Company> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Company create(Company company) {
        sessionFactory.getCurrentSession().save(company.getAddress());
        sessionFactory.getCurrentSession().save(company.getContacts());
        sessionFactory.getCurrentSession().save(company);

        return company;
    }

    @Override
    public Company update(Company company) {
        Session session = sessionFactory.getCurrentSession();

        session.update(company);
        session.update(company.getContacts());
        session.update(company.getAddress());
        session.update(company);
        session.flush();

        return company;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Company company = session.byId(Company.class).load(id);
        session.delete(company);
    }

}
