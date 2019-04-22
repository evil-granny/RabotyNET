package ua.softserve.ita.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.dao.Dao;
import ua.softserve.ita.model.Role;

import javax.annotation.Resource;
import java.util.List;

@Component("roleService")
@org.springframework.stereotype.Service
@Transactional
public class RoleService implements Service<Role> {

    @Resource(name = "roleDao")
    private Dao<Role> roleDao;

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
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
