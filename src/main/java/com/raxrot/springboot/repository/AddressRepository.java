package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
