package ua.softserve.ita.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserve.ita.dao.RoleDao;
import ua.softserve.ita.model.Role;
import ua.softserve.ita.service.RoleService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role findByType(String type) {
        return roleDao.findByType(type);
    }

    @Override
    public Role update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public void deleteById(Long id) {
        roleDao.deleteById(id);
    }

}
