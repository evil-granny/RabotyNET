package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dao.VerificationTokenDao;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.UserAlreadyExistException;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;
import ua.softserve.ita.service.RoleService;
import ua.softserve.ita.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final VerificationTokenDao verificationTokenDao;
    private final RoleService roleService;


    private final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserDao uDao, BCryptPasswordEncoder bCryptPasswordEncoder, VerificationTokenDao verificationTokenDao, RoleService roleService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenDao = verificationTokenDao;
        this.roleService = roleService;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User createDTO(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getLogin())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getLogin());
        }
        User user = new User();
        try {
            user.setLogin(userDto.getLogin());
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            Role role = roleService.findByType("user");
            List < Role > roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            return userDao.save(user);
        } catch (UserAlreadyExistException e) {
            e.getMessage();
        }
        return user;
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Optional<User> findByToken(String token) {
        Long userId = verificationTokenDao.findByToken(token).getUser().getUserId();
        return userDao.findById(userId);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    private boolean emailExists(final String email) {
        return !findByEmail(email).isEmpty();
    }

}
