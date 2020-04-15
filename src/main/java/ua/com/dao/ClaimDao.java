package ua.com.dao;

import ua.com.model.Claim;

import java.util.List;

public interface ClaimDao extends BaseDao<Claim, Long> {

    List<Claim> findAllByCompanyId(Long id);

}
