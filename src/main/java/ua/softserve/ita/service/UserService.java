package ua.softserve.ita.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.UserAlreadyExistException;
import ua.softserve.ita.model.User;

import javax.annotation.Resource;
import java.util.List;

@org.springframework.stereotype.Service("userService")
@Transactional
public class UserService implements UserIService {

    @Resource(name = "userDao")
    private Dao<User> userDao;

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    private final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource(name = "userDao")
    private UserDao uDao;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User create(UserDto userDto) {
        if (emailExists(userDto.getLogin())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getLogin());
        }
        final User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public List<User> findByEmail(String email) {
        return uDao.findByEmail(email);
    }

    private boolean emailExists(final String email) {
        return !findByEmail(email).isEmpty();
    }

}
