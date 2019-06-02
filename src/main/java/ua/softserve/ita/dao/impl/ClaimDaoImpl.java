package ua.softserve.ita.dao.impl;

import org.springframework.stereotype.Repository;
import ua.softserve.ita.dao.ClaimDao;
import ua.softserve.ita.model.Claim;

import java.util.List;

@Repository
public class ClaimDaoImpl extends AbstractDao<Claim, Long> implements ClaimDao {

    private static final String ID = "id";

    @Override
    @SuppressWarnings("unchecked")
    public List<Claim> findAllByCompanyId(Long id) {
        return (List<Claim>) createNamedQuery(Claim.FIND_BY_COMPANY_ID)
                .setParameter(ID, id)
                .getResultList();
    }

}
