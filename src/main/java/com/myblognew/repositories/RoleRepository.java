package com.myblognew.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myblognew.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
	

}
