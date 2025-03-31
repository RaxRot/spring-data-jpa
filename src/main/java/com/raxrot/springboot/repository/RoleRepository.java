package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {   
}
