package ua.softserve.ita.service;

import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenIService {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    void deleteAllExpiredSince(Date now);

    VerificationToken create(VerificationToken verificationToken);

    VerificationToken update(VerificationToken verificationToken);

    void delete(VerificationToken verificationToken);

    VerificationToken createVerificationTokenForUser(final User user, final String token);

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken);

    String validateVerificationToken(String token);

}
