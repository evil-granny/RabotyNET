package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.CVDao;
import ua.softserve.ita.model.CV;
import ua.softserve.ita.utility.QueryUtility;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class CVDaoImpl extends AbstractDao<CV,Long> implements CVDao {
    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    @SuppressWarnings("unchecked")
    public Optional<CV> findByUserId(Long id) {
        return QueryUtility.findOrEmpty(() -> {
            CV result = null;
            try {
                result = (CV) createNamedQuery(CV.FIND_BY_USER_ID)
                        .setParameter(ID, id)
                        .getSingleResult();
            } catch (NoResultException ex) {
                Logger.getLogger(CVDaoImpl.class.getName()).log(Level.WARNING, "CV not found with name " + id);
            }
            return result;
        });
    }

}
