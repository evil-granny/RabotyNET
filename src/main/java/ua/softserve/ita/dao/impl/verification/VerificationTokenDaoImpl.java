package ua.softserve.ita.dao.impl.verification;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.VerificationTokenDao;
import ua.softserve.ita.dao.impl.AbstractDao;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;


@Repository
public class VerificationTokenDaoImpl extends AbstractDao<VerificationToken,Long> implements VerificationTokenDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public VerificationToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from VerificationToken where token = '" + token + "'");
        if (!query.getResultList().isEmpty()) {
            return (VerificationToken) query.getResultList().get(0);
        } else
            return null;
    }

    @Override
    public VerificationToken findByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from VerificationToken where user_id = '" + user.getUserId() + "'");
        if (!query.getResultList().isEmpty()) {
            return (VerificationToken) query.getResultList().get(0);
        } else
            return null;
    }

    @Override
    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from VerificationToken where expirydate = '" + now + "'");
        if (!query.getResultList().isEmpty()) {
            return query.getResultList().stream();
        } else
            return null;
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete  VerificationToken where expirydate = '" + now + "'");
        query.executeUpdate();
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete  VerificationToken where expirydate <= '" + now + "'");
        query.executeUpdate();
    }

    @Override
    public VerificationToken create(VerificationToken verificationToken) {
        sessionFactory.getCurrentSession().save(verificationToken);
        return verificationToken;
    }

    @Override
    public VerificationToken update(VerificationToken verificationToken) {
        Session session = sessionFactory.getCurrentSession();
        session.update(verificationToken);
        session.flush();
        return verificationToken;
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        sessionFactory.getCurrentSession().delete(verificationToken);
    }

    @Override
    public void deleteByUserId(Long userId) {
        //
    }
}
