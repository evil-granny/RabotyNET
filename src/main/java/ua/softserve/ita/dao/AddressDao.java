package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Address;

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
    public Address update(Address address, Long id) {
        Session session = sessionFactory.getCurrentSession();

        Address updatedAddress = session.byId(Address.class).load(id);
        updatedAddress.setStreet(address.getStreet());
        updatedAddress.setCity(address.getCity());
        updatedAddress.setCountry(address.getCountry());
        updatedAddress.setApartment(address.getApartment());
        updatedAddress.setBuilding(address.getBuilding());
        updatedAddress.setZipCode(address.getZipCode());

        session.flush();

        return updatedAddress;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Address address = session.byId(Address.class).load(id);
        session.delete(address);
    }

}
