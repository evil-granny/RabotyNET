package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.UserPrincipal;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.getUserWithRoles(username).orElseThrow(() ->
                new UsernameNotFoundException("No user found with username " + username));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType().toUpperCase()));
        }

        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getUserId()
        );
    }

}
