package ua.softserve.ita.service.token;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.dao.VerificationTokenIDao;
import ua.softserve.ita.model.User;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;


@Component("tokenService")
@org.springframework.stereotype.Service
@Transactional
public class VerificationTokenService implements VerificationTokenIService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Resource(name = "tokenDao")
    private VerificationTokenIDao verificationTokenIDao;

    @Resource(name = "userDao")
    private Dao<User> userDao;

    @Override
    public ua.softserve.ita.model.VerificationToken findByToken(String token) {
        return verificationTokenIDao.findByToken(token);
    }

    @Override
    public ua.softserve.ita.model.VerificationToken findByUser(User user) {
        return verificationTokenIDao.findByUser(user);
    }

    @Override
    public Stream<ua.softserve.ita.model.VerificationToken> findAllByExpiryDateLessThan(Date now) {
        return verificationTokenIDao.findAllByExpiryDateLessThan(now);
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {
        verificationTokenIDao.deleteByExpiryDateLessThan(now);
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        verificationTokenIDao.deleteAllExpiredSince(now);
    }

    @Override
    public ua.softserve.ita.model.VerificationToken create(ua.softserve.ita.model.VerificationToken verificationToken) {
        return verificationTokenIDao.create(verificationToken);
    }

    @Override
    public ua.softserve.ita.model.VerificationToken update(ua.softserve.ita.model.VerificationToken verificationToken) {
        return verificationTokenIDao.update(verificationToken);
    }

    @Override
    public void delete(ua.softserve.ita.model.VerificationToken verificationToken) {
        verificationTokenIDao.delete(verificationToken);
    }

    public ua.softserve.ita.model.VerificationToken createVerificationTokenForUser(final User user, final String token) {
        final ua.softserve.ita.model.VerificationToken myToken = new ua.softserve.ita.model.VerificationToken(token, user);
        return  create(myToken);
    }

    public ua.softserve.ita.model.VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        ua.softserve.ita.model.VerificationToken vToken = verificationTokenIDao.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = verificationTokenIDao.update(vToken);
        return vToken;
    }

    public String validateVerificationToken(String token) {
        final ua.softserve.ita.model.VerificationToken verificationToken = verificationTokenIDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            verificationTokenIDao.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        userDao.update(user);
        return TOKEN_VALID;
    }
}
