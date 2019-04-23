package ua.softserve.ita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.exception.PasswordNotMatchException;
import ua.softserve.ita.exception.UserAlreadyExistException;
import ua.softserve.ita.model.User;
import ua.softserve.ita.validation.EmailValidator;
import ua.softserve.ita.validation.PasswordConstraintValidator;
import ua.softserve.ita.validation.PasswordMatchesValidator;
import ua.softserve.ita.validation.UserValidator;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Component("userService")
@org.springframework.stereotype.Service
@Transactional
public class UserService implements Service<User> {

    @Resource(name = "userDao")
    private Dao<User> userDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    PasswordConstraintValidator passwordConstraintValidator;
//
//    @Autowired
//    PasswordMatchesValidator passwordMatchesValidator;
//
//    @Autowired
//    EmailValidator emailValidator;
//
//    @Autowired
//    UserValidator userValidator;


    @Resource(name = "userDao")
    private UserDao uDao;

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User create(@Valid User user) {
        if (emailExists(user.getLogin())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + user.getLogin());
        }
        final User newUser = new User();
        newUser.setLogin(user.getLogin());

        if (user.getPassword().equals(user.getMatchingPassword())) {
            newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }else{
            throw new PasswordNotMatchException("Passwords don't match");
        }
        return userDao.create(newUser);
    }


    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public User findByEmail(String email) {
        return uDao.findByEmail(email);
    }

    private boolean emailExists(final String email) {
        return findByEmail(email) != null;
    }

}
