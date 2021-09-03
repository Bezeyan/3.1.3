package com.example.springbootcrud.controllers;

import com.example.springbootcrud.model.Role;
import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.UserServiceImpl;
import com.example.springbootcrud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;


@RestController
public class ControllerREST {

    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public ControllerREST(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping({"/api/users"})
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/api/users/{id}")
    public User findById(@PathVariable("id") Long id) throws UserPrincipalNotFoundException {
        return userService.findUserById(id);
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping(value = "/api/users")
    public ResponseEntity<User> editUser(@RequestBody User user) throws UserPrincipalNotFoundException {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping(value = "/api/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping(value = "api/roles")
    public Iterable<Role> findAllRoles() {
        return roleService.findAllRoles();
    }

}
