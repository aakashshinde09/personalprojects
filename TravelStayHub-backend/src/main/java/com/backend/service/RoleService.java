package com.backend.service;

import com.backend.model.Role;
import com.backend.model.User;

import java.util.List;

public interface RoleService {

	List<Role> getRoles();
    Role createRole(Role theRole);

    void deleteRole(Long id);
    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
