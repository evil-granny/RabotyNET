package ua.softserve.ita.dao.profile;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Address;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("addressDao")
@Repository
public class AddressDao implements Dao<Address> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Address findById(Long id) {
        return sessionFactory.getCurrentSession().get(Address.class, id);
    }

    @Override
    public List<Address> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root);
        Query<Address> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Address create(Address address) {
        sessionFactory.getCurrentSession().save(address);

        return address;
    }

    @Override
    public Address update(Address address) {
        Session session = sessionFactory.getCurrentSession();

        session.update(address);
        session.flush();

        return address;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        Address address = session.byId(Address.class).load(id);
        session.delete(address);
    }

}
