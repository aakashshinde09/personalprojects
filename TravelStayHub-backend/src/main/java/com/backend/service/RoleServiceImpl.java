package com.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.exception.RoleAlreadyExistException;
import com.backend.exception.UserAlreadyExistsException;
import com.backend.model.Role;
import com.backend.model.User;
import com.backend.repository.BookingRepo;
import com.backend.repository.RoleRepo;
import com.backend.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepo roleRepo = null;
    private final UserRepo userRepo = null;

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepo.existsByName(roleName)){
            throw new RoleAlreadyExistException(theRole.getName()+" role already exists");
        }
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepo.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepo.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Role>  role = roleRepo.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepo.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Role>  role = roleRepo.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+ " is already assigned to the" + role.get().getName()+ " role");
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepo.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepo.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepo.save(role.get());
    }
}
