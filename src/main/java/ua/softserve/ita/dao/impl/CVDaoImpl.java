package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.CVDao;
import ua.softserve.ita.model.CV;

@Repository
public class CVDaoImpl extends AbstractDao<CV,Long> implements CVDao {
    private static final String ID = "id";

    @Override
    @SuppressWarnings("unchecked")
    public CV findByUserId(Long id) {
        return (CV) createNamedQuery(CV.FIND_BY_USER_ID)
                .setParameter(ID, id)
                .getResultList();
    }

}
