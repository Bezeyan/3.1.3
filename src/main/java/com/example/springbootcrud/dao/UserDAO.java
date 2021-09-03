package com.example.springbootcrud.dao;

import com.example.springbootcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    UserDetails getUsersByEmail(String email);
}
