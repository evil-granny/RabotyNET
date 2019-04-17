package ua.softserve.ita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.UserRepository;
import ua.softserve.ita.model.User;

@Service(value = "userDetailsService")
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        if (user != null){
            return user;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
