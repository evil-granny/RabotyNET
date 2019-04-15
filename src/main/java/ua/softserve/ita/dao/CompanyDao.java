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
import java.util.List;

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
    public Long insert(Company company) {
        sessionFactory.getCurrentSession().save(company);
        return company.getCompanyId();
    }

    @Override
    public Long update(Company company, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Company updatedCompany = sessionFactory.getCurrentSession().byId(Company.class).load(id);
        updatedCompany.setCompanyId(company.getCompanyId());
        updatedCompany.setContacts(company.getContacts());
        updatedCompany.setAddress(company.getAddress());
        updatedCompany.setEdrpou(company.getEdrpou());
        updatedCompany.setDescription(company.getDescription());
        updatedCompany.setLogo(company.getLogo());
        updatedCompany.setName(company.getName());
        updatedCompany.setWebsite(company.getWebsite());
        updatedCompany.setVacancies(company.getVacancies());
        session.flush();

        return id;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Company company = session.byId(Company.class).load(id);
        session.delete(company);
    }

}
