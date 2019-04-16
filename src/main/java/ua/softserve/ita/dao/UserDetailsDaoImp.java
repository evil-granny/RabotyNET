package ua.softserve.ita.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.softserve.ita.model.User;

@Repository
public class UserDetailsDaoImp implements UserDetailsDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findUserByLogin(String login) {
        return sessionFactory.getCurrentSession().get(User.class, login);
    }
}
