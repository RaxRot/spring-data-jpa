package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
