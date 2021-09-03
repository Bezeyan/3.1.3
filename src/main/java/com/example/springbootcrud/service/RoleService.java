package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Role;

public interface RoleService {

    void addRole(Role role);

    Role getRoleById(Long id);

    Iterable<Role> findAllRoles();

}
