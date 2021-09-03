package com.example.springbootcrud.dao;

import com.example.springbootcrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role,Long> {
}
