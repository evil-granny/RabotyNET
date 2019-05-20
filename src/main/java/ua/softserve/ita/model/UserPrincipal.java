package ua.softserve.ita.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    public static final UserPrincipal UNKNOWN_USER = new UserPrincipal("anonymous", "", Collections.EMPTY_LIST, -1L);

    private Long userId;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

}
