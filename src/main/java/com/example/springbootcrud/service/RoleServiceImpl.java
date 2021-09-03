package com.example.springbootcrud.service;

import com.example.springbootcrud.dao.RoleDAO;
import com.example.springbootcrud.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public void addRole(Role role) {
        roleDAO.save(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDAO.getById(id);
    }

    @Override
    public  Iterable<Role> findAllRoles() {
        return roleDAO.findAll();
    }

}
