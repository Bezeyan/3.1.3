package com.example.springbootcrud.initializer;

import com.example.springbootcrud.model.Role;
import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.RoleService;
import com.example.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserInitImpl implements UserInit{
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserInitImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        roleService.addRole(new Role("ROLE_USER"));
        roleService.addRole(new Role("ROLE_ADMIN"));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(1l));
        userService.addUser(
                new User("user", "U", "user", (byte) 26, "user@mail.ru", roles ));
        roles.add(roleService.getRoleById(2l));
        userService.addUser(
                new User("admin", "A", "admin", (byte) 26,"admin@mail.ru", roles ));
    }
}
