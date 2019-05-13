package ua.softserve.ita.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.User;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
@Primary
@Transactional
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {
    private static final Logger lOGGER = Logger.getLogger(UserDao.class.getName());

    @Override
    public User findUserByUsername(String username) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("select u from User u join u.roles where u.login = :login", User.class);
        query.setParameter("login", username);
        lOGGER.severe("QUERY = " + query.getSingleResult());
        return query.getSingleResult();
    }


    @Override
    public List<User> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where login like '" + email + "%'");
        return query.getResultList();
    }

        @Override
        public Optional<User> findById(Long id) {
            Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where id = id");
        return Optional.ofNullable((User) query.getResultList().get(0));
    }

}
