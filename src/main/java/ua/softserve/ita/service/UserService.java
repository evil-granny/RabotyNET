package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.User;

import javax.annotation.Resource;
import java.util.List;

@Component("userService")
@org.springframework.stereotype.Service
@Transactional
public class UserService implements Service<User> {
    @Resource(name = "userDao")
    private Dao<User> userDao;

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public User update(User user, Long id) {
        return userDao.update(user, id);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
