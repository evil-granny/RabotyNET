package ua.softserve.ita.dao.profile;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Contact;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("contactDao")
@Repository
public class ContactDao implements Dao<Contact> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Contact findById(Long id) {
        return sessionFactory.getCurrentSession().get(Contact.class, id);
    }

    @Override
    public List<Contact> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> root = criteriaQuery.from(Contact.class);
        criteriaQuery.select(root);
        Query<Contact> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Contact create(Contact contact) {
        sessionFactory.getCurrentSession().save(contact);

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        Session session = sessionFactory.getCurrentSession();

        session.update(contact);
        session.flush();

        return contact;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Contact contact = session.byId(Contact.class).load(id);

        session.delete(contact);
    }

}
