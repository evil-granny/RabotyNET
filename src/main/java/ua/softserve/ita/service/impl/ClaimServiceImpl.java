package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.ClaimDao;
import ua.softserve.ita.model.Claim;
import ua.softserve.ita.service.ClaimService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimDao claimDao;

    @Autowired
    public ClaimServiceImpl(ClaimDao claimDao) {
        this.claimDao = claimDao;
    }

    @Override
    public Optional<Claim> findById(Long id) {
        return claimDao.findById(id);
    }

    @Override
    public List<Claim> findAll() {
        return claimDao.findAll();
    }

    @Override
    public Claim save(Claim claim) {
        return claimDao.save(claim);
    }

    @Override
    public Claim update(Claim claim) {
        return claimDao.update(claim);
    }

    @Override
    public void deleteById(Long id) {
        claimDao.deleteById(id);
    }

    @Override
    public List<Claim> findAllByCompanyId(Long id) {
        return claimDao.findAllByCompanyId(id);
    }

}
