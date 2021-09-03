package com.example.springbootcrud.service;

import com.example.springbootcrud.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;


public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User addUser(User user);

    User updateUser(User user) throws UserPrincipalNotFoundException;

    User findUserById(Long id) throws UserPrincipalNotFoundException;

    void deleteUserById(Long id);

}
