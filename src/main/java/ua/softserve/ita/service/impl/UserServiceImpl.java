package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dto.UserDto;
import ua.softserve.ita.exception.UserAlreadyExistException;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.model.profile.Person;
import ua.softserve.ita.service.PersonService;
import ua.softserve.ita.service.RoleService;
import ua.softserve.ita.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
class UserServiceImpl implements UserService {

    private static final String USER = "user";

    private final UserDao userDao;

    private final RoleService roleService;
    private final PersonService personService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService, PersonService personService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.personService = personService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    public User createDto(UserDto userDto) {
        if (emailExists(userDto.getLogin())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getLogin());
        }

        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        Role role = roleService.findByType(USER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        Person person = new Person();
        person.setUserId(userDao.save(user).getUserId());
        Contact contact = new Contact();
        contact.setEmail(user.getLogin());
        person.setContact(contact);
        personService.save(person);

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
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean emailExists(final String email) {
        return findByEmail(email).isPresent();
    }

}
