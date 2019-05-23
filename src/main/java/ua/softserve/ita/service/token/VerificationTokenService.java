package ua.softserve.ita.service.token;

import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    VerificationToken findByToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteByUserId(Long userId);

    void deleteAllExpiredSince();

    VerificationToken create(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void delete(VerificationToken verificationToken);

    VerificationToken createVerificationTokenForUser(final User user, final String token);


    String validateVerificationToken(String token);

}
