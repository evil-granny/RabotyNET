package ua.softserve.ita.dao.hibernate;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.dao.UserRepository;
import ua.softserve.ita.model.User;

//@Repository(value = "userDao")
//public class HUserDao extends Dao<User> implements UserRepository<User> {
//
//    @Override
//    public User getUserByUsername(String username) {
//        String request = "select u from public.user where public.user.login = :login";
//        Query<User> query = getSessionFactory().createQuery(request);
//        query.setParameter("login", username);
//        return query.getSingleResult(); // todo throw exception. how would be better: catch there and return null or catch in controller and do something if username does not exist
//    }
//}
