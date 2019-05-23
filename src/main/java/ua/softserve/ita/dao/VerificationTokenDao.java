package ua.softserve.ita.dao;

import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Date;
import java.util.Optional;

public interface VerificationTokenDao {

    VerificationToken findByToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteAllExpiredSince(Date now);

    VerificationToken create(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void delete(VerificationToken verificationToken);

    void deleteByUserId(Long userId);
}
