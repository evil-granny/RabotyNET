package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.dao.VerificationTokenIDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;


@Component("tokenService")
@org.springframework.stereotype.Service
@Transactional
public class VerificationTokenService implements VerificationTokenIService{

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "http://localhost:8080/";
    public static String APP_NAME = "RabotyNet";

    @Resource(name = "tokenDao")
    private VerificationTokenIDao verificationTokenIDao;

    @Resource(name = "userDao")
    private Dao<User> userDao;

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenIDao.findByToken(token);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenIDao.findByUser(user);
    }

    @Override
    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
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
    public VerificationToken create(VerificationToken verificationToken) {
        return verificationTokenIDao.create(verificationToken);
    }

    @Override
    public VerificationToken update(VerificationToken verificationToken) {
        return verificationTokenIDao.update(verificationToken);
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        verificationTokenIDao.delete(verificationToken);
    }

    public VerificationToken createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        return  create(myToken);
    }

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = verificationTokenIDao.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = verificationTokenIDao.update(vToken);
        return vToken;
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenIDao.findByToken(token);
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
