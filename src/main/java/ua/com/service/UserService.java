package ua.com.service;

import ua.com.exception.UserAlreadyExistException;
import ua.com.dto.UserDto;
import ua.com.model.Role;
import ua.com.model.User;

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

    List<String> getUserRoles(String userName);

}
