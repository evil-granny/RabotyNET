package ua.softserve.ita.dao;

import ua.softserve.ita.model.User;

public interface UserDetailsDao{

    User findUserByUsername(String username);
}