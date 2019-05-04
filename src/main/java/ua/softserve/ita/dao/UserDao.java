package ua.softserve.ita.dao;

import ua.softserve.ita.model.User;

import java.util.List;

public interface UserDao extends BaseDao<User, Long> {
    User findUserByUsername(String username);

    public List<User> findByEmail(String email);
}
