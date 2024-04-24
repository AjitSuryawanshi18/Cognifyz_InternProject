package com.example.internproject.config;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.internproject.Repos.MyUserRepository;
import com.example.internproject.model.MyUser;






//Second step create service( checking user is fetching or not? ) : to work with security i.e. when we use security it will provide default security if we want to mold it as per our requirements then we have to follow this steps :
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MyUserRepository myUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		 Optional<MyUser> user = myUserRepository.findByEmail(username);
//		 Optional<MyUser> user = myUserRepository.findByEmail(username);
		 
//			System.out.println(user); // printing for checking purpose
//			System.out.println(username); // printing for checking purpose

		 
		 if(user.isPresent()) {
			 
			 var userObj = user.get();
			 return User.builder()
					 .username(userObj.getEmail())// i am getting issue here because of i am trying to login with email and checking with username 
					 .password(userObj.getPassword())
					 .roles(getRoles(userObj))
					 .build();
			 
		 }else {
			 throw new UsernameNotFoundException(username);
		 }


	}

	private String[] getRoles(MyUser user) {
		
		if(user.getRole() == null) {
			return new String[] {"USER"};
		}
		return user.getRole().split(",");
	}



}
