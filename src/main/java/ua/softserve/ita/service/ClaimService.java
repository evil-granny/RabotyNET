package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Claim;

import javax.annotation.Resource;
import java.util.List;

@Component("claimService")
@org.springframework.stereotype.Service
@Transactional
public class ClaimService implements Service<Claim> {

    @Resource(name = "claimDao")
    private Dao<Claim> claimDao;

    @Override
    public Claim findById(Long id) {
        return claimDao.findById(id);
    }

    @Override
    public List<Claim> findAll() {
        return claimDao.findAll();
    }

    @Override
    public Claim create(Claim claim) {
        return claimDao.create(claim);
    }

    @Override
    public Claim update(Claim claim) {
        return claimDao.update(claim);
    }

    @Override
    public void deleteById(Long id) {
        claimDao.deleteById(id);
    }
}
