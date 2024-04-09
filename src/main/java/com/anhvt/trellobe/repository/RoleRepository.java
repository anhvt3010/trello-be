package com.anhvt.trellobe.repository;

import com.anhvt.trellobe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role, String> {
}
