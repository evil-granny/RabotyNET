package ua.softserve.ita.service.token;

import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenIService {

    ua.softserve.ita.model.VerificationToken findByToken(String token);

    ua.softserve.ita.model.VerificationToken findByUser(User user);

    Stream<ua.softserve.ita.model.VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    void deleteAllExpiredSince(Date now);

    ua.softserve.ita.model.VerificationToken create(ua.softserve.ita.model.VerificationToken verificationToken);

    ua.softserve.ita.model.VerificationToken update(ua.softserve.ita.model.VerificationToken verificationToken);

    void delete(ua.softserve.ita.model.VerificationToken verificationToken);

    ua.softserve.ita.model.VerificationToken createVerificationTokenForUser(final User user, final String token);

    public ua.softserve.ita.model.VerificationToken generateNewVerificationToken(final String existingVerificationToken);

    String validateVerificationToken(String token);

}
