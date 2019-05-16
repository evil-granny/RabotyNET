package ua.softserve.ita.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;

@Repository
public class RoleDaoImpl extends AbstractDao<Role, Long> implements RoleDao {
    private static final String USER_ROLE = "ROLE_USER";

    public Role findByType(String type) {
        Session session = sessionFactory.getCurrentSession();
        // Query query = session.createQuery("from Role where type =:type").setParameter("type",type);
        //return (Role) query.getSingleResult();
        return new Role(USER_ROLE);
    }

    public void addRoleToUser(User user, String typeRole){
        Session session = sessionFactory.getCurrentSession();

    }

}
