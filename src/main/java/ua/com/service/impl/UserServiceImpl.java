package ua.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.dao.UserDao;
import ua.com.exception.UserAlreadyExistException;
import ua.com.model.profile.Contact;
import ua.com.model.profile.Person;
import ua.com.dto.UserDto;
import ua.com.model.Role;
import ua.com.model.User;
import ua.com.service.PersonService;
import ua.com.service.RoleService;
import ua.com.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> getUserRoles(String userName) {
        User user = userDao.getUserWithRoles(userName)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with userName " + userName));
        return user.getRoles().stream().map(Role::getType).collect(Collectors.toList());
    }

}
