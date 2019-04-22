package ua.softserve.ita.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

@Primary
@Component("userDao")
@Repository
@Transactional
public class UserDao implements Dao<User>, UserDetailsDao {

    private static final Logger lOGGER = Logger.getLogger(UserDao.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findById(Long id) {
        return sessionFactory.getCurrentSession().get(User.class,id);
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Query<User> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Long insert(User user) {
        sessionFactory.getCurrentSession().save(user);
        return user.getUserId();
    }

    @Override
    public Long update(User user, Long id) {
        Session session = sessionFactory.getCurrentSession();
        User updatedUser = session.byId(User.class).load(id);
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRoles(user.getRoles());
        session.flush();

        return id;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.byId(User.class).load(id);
        session.delete(user);
    }

    @Override
    public User findUserByUsername(String username) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("select u from User u join u.roles where u.login = :login", User.class);
//        Query<User> query = sessionFactory.getCurrentSession().createQuery("select new User(u.login, u.password, elements(r.type)) from User u join u.roles r where u.login = :login", User.class);
        query.setParameter("login", username);
        lOGGER.severe("QUERY = " + query.getSingleResult());
        return query.getSingleResult();

//        return sessionFactory.getCurrentSession().get(User.class,login);
    }
}
