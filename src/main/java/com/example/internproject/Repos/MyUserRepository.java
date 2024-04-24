package com.example.internproject.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.internproject.model.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    
	Optional<MyUser> findByEmail(String email);
	
	 List<MyUser> findByRole(String role);
	
   
	
//	Optional<MyUser> findByUsername(String username);   made by the trainer
	
	
	
}
