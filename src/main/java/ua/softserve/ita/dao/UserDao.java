package ua.softserve.ita.dao;

import ua.softserve.ita.model.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, Long> {

    Optional<User> findUserWithRolesByLogin(String username);

    Optional<User> findByEmail(String email);

}
