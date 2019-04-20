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
    public Role insert(Role role) {
        sessionFactory.getCurrentSession().save(role);
        return role;
    }

    @Override
    public Role update(Role role, Long id) {
        Session session = sessionFactory.getCurrentSession();
        Role updatedRole = sessionFactory.getCurrentSession().byId(Role.class).load(id);
        updatedRole.setRoleId(role.getRoleId());
        updatedRole.setType(role.getType());
        updatedRole.setUsers(role.getUsers());
        session.flush();

        return updatedRole;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Role role = session.byId(Role.class).load(id);
        session.delete(role);
    }
}
