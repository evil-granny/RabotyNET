package ua.softserve.ita.dao.impl.verification;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.VerificationTokenDao;
import ua.softserve.ita.dao.impl.AbstractDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;
import ua.softserve.ita.utility.QueryUtility;

import javax.persistence.NoResultException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;


@Repository
public class VerificationTokenDaoImpl extends AbstractDao<VerificationToken, Long> implements VerificationTokenDao {

    private static final String ID = "id";
    private static final String TOKEN = "token";
    private static final String EXPIRY_DATE = "expiryDate";

    private Logger logger = Logger.getLogger(VerificationTokenDaoImpl.class.getName());

    public VerificationTokenDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<VerificationToken> findVerificationToken(String token) {
        return QueryUtility.findOrEmpty(() -> {
            VerificationToken verificationToken = null;
            try {
                verificationToken = (VerificationToken) createNamedQuery(VerificationToken.FIND_VERIFICATION_TOKEN)
                        .setParameter(TOKEN, token)
                        .getSingleResult();
            } catch (NoResultException ex) {
                logger.warning("Token not found");
            }

            return verificationToken;
        });
    }

    @Override
    public Optional<VerificationToken> findByUser(User user) {
        return QueryUtility.findOrEmpty(() -> {
            VerificationToken result = null;
            try {
                result = (VerificationToken) createNamedQuery(VerificationToken.FIND_TOKEN_BY_USER)
                        .setParameter(ID, user.getUserId())
                        .getSingleResult();
            } catch (NoResultException ex) {
                logger.warning("Token for user " + user.getUsername() + "not found");
            }

            return result;
        });
    }

    @Override
    public void deleteAllExpiredSince() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from VerificationToken vt where vt.expiryDate = :expiryDate")
                .setParameter(EXPIRY_DATE, Date.from(Instant.now()));
        query.executeUpdate();
    }

}
