package ua.softserve.ita.service;

import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.UserAlreadyExistException;
import ua.softserve.ita.model.User;

import java.util.List;

public interface UserIService {

    User findById(Long id);

    List<User> findAll();

    User create(UserDto userDto) throws UserAlreadyExistException;;

    User update(User user);

    void deleteById(Long id);



}
