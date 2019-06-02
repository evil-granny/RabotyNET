package ua.softserve.ita.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.UserDao;
import ua.softserve.ita.dao.VerificationTokenDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Calendar;
import java.util.Optional;


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
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenDao.findVerificationToken(token);
    }

    @Override
    public Optional<VerificationToken> findByUser(User user) {
        return verificationTokenDao.findByUser(user);
    }

    @Override
    public void deleteAllExpiredSince() {
        verificationTokenDao.deleteAllExpiredSince();
    }

    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        return verificationTokenDao.save(verificationToken);
    }

    @Override
    public VerificationToken update(VerificationToken verificationToken) {
        return verificationTokenDao.update(verificationToken);
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        verificationTokenDao.delete(verificationToken);
    }


    public String validateVerificationToken(String token) {
        final Calendar cal = Calendar.getInstance();
        if (verificationTokenDao.findVerificationToken(token).isPresent()) {
            VerificationToken verificationToken = verificationTokenDao.findVerificationToken(token).get();
            if ((verificationToken.getExpiryDate()
                    .getTime()
                    - cal.getTime()
                    .getTime()) <= 0) {
                verificationTokenDao.delete(verificationToken);
                return TOKEN_EXPIRED;
            } else {
                User user = verificationToken.getUser();
                user.setEnabled(true);
                userDao.update(user);
                verificationTokenDao.delete(verificationToken);
                return TOKEN_VALID;
            }
        } else
            return TOKEN_INVALID;
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        save(verificationToken);
    }

}
