package ua.softserve.ita.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dao.VerificationTokenDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;



@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final String TOKEN_INVALID = "invalidToken";
    private static final String TOKEN_EXPIRED = "expired";
    private static final String TOKEN_VALID = "valid";

    private final VerificationTokenDao verificationTokenDao;
    private final UserDao userDao;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenDao verificationTokenDao, UserDao userDao) {
        this.verificationTokenDao = verificationTokenDao;
        this.userDao = userDao;
    }


    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    @Override
    public Optional<VerificationToken> findByUser(User user) {
        return verificationTokenDao.findByUser(user);
    }

    @Override
    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
        return verificationTokenDao.findAllByExpiryDateLessThan(now);
    }

    @Override
    public void deleteByExpiryDateLessThan(Date expiredDate) {
        verificationTokenDao.deleteByExpiryDateLessThan(expiredDate);
    }

    @Override
    public void deleteByUserId(Long userId) {
        verificationTokenDao.deleteByUserId(userId);
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        verificationTokenDao.deleteAllExpiredSince(now);
    }

    @Override
    public VerificationToken create(VerificationToken verificationToken) {
        return verificationTokenDao.create(verificationToken);
    }

    @Override
    public VerificationToken update(VerificationToken verificationToken) {
        return verificationTokenDao.update(verificationToken);
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        verificationTokenDao.delete(verificationToken);
    }

    public VerificationToken createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        return  create(myToken);
    }

    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = verificationTokenDao.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = verificationTokenDao.update(vToken);
        return vToken;
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            verificationTokenDao.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        userDao.update(user);
        return TOKEN_VALID;
    }
}
