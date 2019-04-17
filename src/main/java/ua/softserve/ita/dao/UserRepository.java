package ua.softserve.ita.dao;

import ua.softserve.ita.model.User;

public interface UserRepository extends Dao<User> {

    User getUserByUsername(String username);
}
