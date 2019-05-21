package ua.softserve.ita.dao.impl;

import org.hibernate.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.utility.QueryUtility;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Primary
@Transactional
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {
    private static final Logger lOGGER = Logger.getLogger(UserDao.class.getName());
    private static final String ID = "id";
    private static final String LOGIN = "login";

    @Override
    public User findUserByUsername(String username) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("select u from User u join u.roles where u.login = :login", User.class);
        query.setParameter("login", username);
        lOGGER.severe("QUERY = " + query.getSingleResult());
        return query.getSingleResult();
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return QueryUtility.findOrEmpty(() -> {
            User result = null;
            try {
                result = (User) createNamedQuery(User.FIND_USER_BY_EMAIL)
                        .setParameter(LOGIN, email)
                        .getSingleResult();
            } catch (NoResultException ex) {
                Logger.getLogger(UserDaoImpl.class.getName()).log(Level.WARNING, "User not found with email " + email);
            }
            return result;
        });
    }

        @Override
        public Optional<User> findById(Long id) {
            return QueryUtility.findOrEmpty(() -> {
                User result = null;
                try {
                    result = (User) createNamedQuery(User.FIND_USER_BY_ID)
                            .setParameter(ID, id)
                            .getSingleResult();
                } catch (NoResultException ex) {
                    Logger.getLogger(UserDaoImpl.class.getName()).log(Level.WARNING, "User not found with id " + id);
                }
                return result;
            });
    }

}
