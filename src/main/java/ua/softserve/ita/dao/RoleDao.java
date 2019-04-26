package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.model.Role;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("roleDao")
@Repository
public class RoleDao implements Dao<Role> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role findById(Long id) {
        return sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root);
        Query<Role> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Role create(Role role) {
        sessionFactory.getCurrentSession().save(role);

        return role;
    }

    @Override
    public Role update(Role role) {
        Session session = sessionFactory.getCurrentSession();

        session.update(role);
        session.flush();

        return role;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Role role = session.byId(Role.class).load(id);
        session.delete(role);
    }

    public Role findByType(String type) {
            Session session = sessionFactory.getCurrentSession();
           // Query query = session.createQuery("from Role where type =:type").setParameter("type",type);
            //return (Role) query.getSingleResult();
            return new Role("ROLE_USER");
    }
}
