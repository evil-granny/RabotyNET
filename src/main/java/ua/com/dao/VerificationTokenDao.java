package ua.com.dao;

import ua.com.model.VerificationToken;
import ua.com.model.User;

import java.util.Optional;

public interface VerificationTokenDao extends BaseDao<VerificationToken, Long> {

    Optional<VerificationToken> findVerificationToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteAllExpiredSince();

}
