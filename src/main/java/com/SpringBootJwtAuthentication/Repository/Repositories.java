package com.SpringBootJwtAuthentication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBootJwtAuthentication.Entity.Users;

@org.springframework.stereotype.Repository
public interface Repositories extends JpaRepository<Users, Integer> {
	
	 Optional<Users> findByUsername(String username);

}
