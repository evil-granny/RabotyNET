package ua.com.dao;

import ua.com.model.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, Long> {

    Optional<User> getUserWithRoles(String username);

    Optional<User> findByEmail(String email);

}
