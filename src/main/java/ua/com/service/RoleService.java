package ua.com.service;

import ua.com.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);

    List<Role> findAll();

    Role save(Role role);

    Role findByType(String type);

    Role update(Role role);

    void deleteById(Long id);

}
