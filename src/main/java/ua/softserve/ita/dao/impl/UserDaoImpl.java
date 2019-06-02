package ua.softserve.ita.dao.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.utility.QueryUtility;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
@Primary
@Transactional
public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    private static final String LOGIN = "login";

    private Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Override
    public Optional<User> getUserWithRoles(String login) {
        return QueryUtility.findOrEmpty(() -> {
            User result = null;
            try {
                result = (User) createNamedQuery(User.GET_USER_WITH_ROLES)
                        .setParameter(LOGIN, login)
                        .getSingleResult();
            } catch (NoResultException ex) {
                logger.warning("User not found!");
            }

            return result;
        });
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
                logger.warning("User not found with email " + email);
            }

            return result;
        });
    }

}
