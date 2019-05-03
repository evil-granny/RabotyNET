package ua.softserve.ita.dao.profile;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.profile.Contacts;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("contactsDao")
@Repository
public class ContactsDao implements Dao<Contacts> {

    private final SessionFactory sessionFactory;

    public ContactsDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Contacts findById(Long id) {
        return sessionFactory.getCurrentSession().get(Contacts.class, id);
    }

    @Override
    public List<Contacts> findAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Contacts> criteriaQuery = criteriaBuilder.createQuery(Contacts.class);
        Root<Contacts> root = criteriaQuery.from(Contacts.class);
        criteriaQuery.select(root);
        Query<Contacts> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Contacts create(Contacts contacts) {
        sessionFactory.getCurrentSession().save(contacts);

        return contacts;
    }

    @Override
    public Contacts update(Contacts contacts) {
        Session session = sessionFactory.getCurrentSession();

        session.update(contacts);
        session.flush();

        return contacts;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();

        Contacts contacts = session.byId(Contacts.class).load(id);
        session.delete(contacts);
    }

}
