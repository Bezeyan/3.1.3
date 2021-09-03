package com.example.springbootcrud.service;

import com.example.springbootcrud.dao.UserDAO;
import com.example.springbootcrud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    @Override
    public List<User> getAllUsers() {
       return userDAO.findAll();
    }

    @Override
    public User addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
       return userDAO.save(user);
    }

    @Override
    public User updateUser(User user) throws UserPrincipalNotFoundException {

        if (user.getPassword().equals(findUserById(user.getId()).getPassword())) {
            user.setPassword(findUserById(user.getId()).getPassword());
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return userDAO.save(user);
    }

    @Override
    public User findUserById(Long id) throws UserPrincipalNotFoundException {
        return userDAO.findById(id).orElseThrow(() -> new UserPrincipalNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.getUsersByEmail(s);
    }

}
