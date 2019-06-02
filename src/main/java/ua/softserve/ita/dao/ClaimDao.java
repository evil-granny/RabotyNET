package ua.softserve.ita.dao;

import ua.softserve.ita.model.Claim;

import java.util.List;

public interface ClaimDao extends BaseDao<Claim, Long> {

    List<Claim> findAllByCompanyId(Long id);

}
