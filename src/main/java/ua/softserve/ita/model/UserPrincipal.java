package ua.softserve.ita.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    private Long userId;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

}
