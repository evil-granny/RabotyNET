package ua.com.dao;

import ua.com.model.Role;

public interface RoleDao extends BaseDao<Role, Long> {

    Role findByType(String type);

}
