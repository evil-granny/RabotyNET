package ua.softserve.ita.service;

import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.UserAlreadyExistException;
import ua.softserve.ita.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    User createDto(UserDto userDto) throws UserAlreadyExistException;

    User save(User user);

    User update(User user);

    void deleteById(Long id);

    boolean emailExists(String email);

}
