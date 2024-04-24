package com.example.internproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.internproject.Repos.MyUserRepository;
import com.example.internproject.model.MyUser;

@Service
public class UserService {

	 @Autowired
	    private MyUserRepository myUserRepository; // autowiring  UserRepository

	    public List<MyUser> getUsersWithRole(String role) {
	        return myUserRepository.findByRole(role);
	    }
	
}
