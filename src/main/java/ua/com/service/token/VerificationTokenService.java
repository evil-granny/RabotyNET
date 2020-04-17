package ua.com.service.token;

import ua.com.model.VerificationToken;
import ua.com.model.User;

import java.util.Optional;

public interface VerificationTokenService {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUser(User user);

    void deleteAllExpiredSince();

    VerificationToken save(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void delete(VerificationToken verificationToken);

    String validateVerificationToken(String token);

    void createVerificationTokenForUser(User user, String token);

}
