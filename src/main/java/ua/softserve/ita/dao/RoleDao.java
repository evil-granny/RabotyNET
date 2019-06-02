package ua.softserve.ita.dao;

import ua.softserve.ita.model.Role;

public interface RoleDao extends BaseDao<Role, Long> {

    Role findByType(String type);

}
