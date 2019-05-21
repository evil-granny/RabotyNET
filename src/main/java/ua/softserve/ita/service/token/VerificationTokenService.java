package ua.softserve.ita.service.token;

import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public interface VerificationTokenService {

    VerificationToken findByToken(String token);

    Optional<VerificationToken> findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    void deleteByUserId(Long userId);

    void deleteAllExpiredSince(Date now);

    VerificationToken create(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void delete(VerificationToken verificationToken);

    VerificationToken createVerificationTokenForUser(final User user, final String token);

    VerificationToken generateNewVerificationToken(final String existingVerificationToken);

    String validateVerificationToken(String token);

}
