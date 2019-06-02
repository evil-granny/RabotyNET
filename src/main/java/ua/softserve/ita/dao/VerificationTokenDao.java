package ua.softserve.ita.dao;

import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao extends BaseDao<VerificationToken, Long> {

    Optional<VerificationToken> findVerificationToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteAllExpiredSince();

}
