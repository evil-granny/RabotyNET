package ua.softserve.ita.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    public final static UserPrincipal UNKNOWN_USER = new UserPrincipal("anonymous", "", Collections.EMPTY_LIST, -1L);

    private long userID;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities, long userID) {
        super(username, password, authorities);
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }
}
