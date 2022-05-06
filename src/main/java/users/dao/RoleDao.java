package users.dao;



import users.model.Role;

import java.util.List;

public interface RoleDao {

    List<Role> getAllRoles();

    Role findById(Long id);

    void saveRole(Role role);
}
