//this controller will only handle the requests which comes from User Who registered  have already entry in database and the user who LOged in with OAuth2 

package com.example.internproject.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.internproject.Repos.MyUserRepository;
import com.example.internproject.model.MyUser;

import java.time.LocalDate;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserHomeController {
	
	@Autowired
	private MyUserRepository myUserRepository;
	
	@Autowired
	private MyUserRepository userRepository;
	

	    @GetMapping("/home")
	    public String userLogin(Authentication authentication,Model model) {
	    	
	    	// this if condition checks is user is logged in with Google OAuth2
	        if (authentication.getPrincipal() instanceof DefaultOidcUser) { 
	            // User logged in via Google OAuth
	            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
	            String email = oidcUser.getEmail();
	            String name = oidcUser.getFullName();
	            String givenName = oidcUser.getGivenName();

	            // Encoding dummy password
	            String password = "Dummy";
	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            String encodedPassword = passwordEncoder.encode(password);

	            // Check if user already exists in the database
	            Optional<MyUser> userFromOAuth2 = myUserRepository.findByEmail(email);
	            if (!userFromOAuth2.isPresent()) {
	                // User does not exist, create a new user
	                MyUser user = new MyUser();
	                user.setFullName(name);
	                user.setEmail(email);
	                user.setPassword(encodedPassword); // Save encoded password
	                user.setConfirmPassword(encodedPassword);
	                user.setUsername(givenName);
	                LocalDate dob = LocalDate.of(1999, 07, 18);
	                user.setDateOfBirth(dob);
	                user.setGender("Undefined");
	                user.setPhoneNo("9987456321");
	                user.setTermsAgreement(true);
	                user.setRole("USER");
	                myUserRepository.save(user); // Save user in the database
	            }
	           
	        
	        }// this if condition checks is user is logged in with Github OAuth2
	        else if (authentication.getPrincipal() instanceof OAuth2User) {
//	        	System.out.println("inside github checking controller");
	            // User logged in via GitHub OAuth
	            OAuth2User oauthToken = (OAuth2User) authentication.getPrincipal();
	            String gitlogin = oauthToken.getAttribute("login");
	            String login= gitlogin +"@gmail.com";
	            String email = login.toLowerCase();
//	            System.out.println("github user data fetched from controller : "+email);

	            String name= "githubUser";
	            String givenName = "githubUser";
	            // Encoding dummy password
	            String password = "Dummy";
	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            String encodedPassword = passwordEncoder.encode(password);

	            // Check if user already exists in the database
	            Optional<MyUser> userFromOAuth2 = myUserRepository.findByEmail(email);
	            if (!userFromOAuth2.isPresent()) {
	                // User does not exist, create a new user
	                MyUser user = new MyUser();
	                user.setFullName(name);
	                user.setEmail(email);
	                user.setPassword(encodedPassword); // Save encoded password
	                user.setConfirmPassword(encodedPassword);
	                user.setUsername(givenName);
	                LocalDate dob = LocalDate.of(1999, 07, 18);
	                user.setDateOfBirth(dob);
	                user.setGender("Undefined");
	                user.setPhoneNo("9987456321");
	                user.setTermsAgreement(true);
	                user.setRole("USER");
	                myUserRepository.save(user); // Save user in the database
	           
	            }
	            
	        }
	        
	        
	        
//	        this below code is providing us which user logged in and then we are fetching username and setting it to user home as we can know which user is logged in
	        
	        SecurityContext securityContext = SecurityContextHolder.getContext();
			if(securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
			model.addAttribute("username", user.getAttribute("name")!= null ?user.getAttribute("name"):user.getAttribute("login"));
//			System.out.println("authenticated user : gone from here");

			}else {
			
				String email = authentication.getName();
				Optional<MyUser> user = myUserRepository.findByEmail(email);
				String username = user.get().getUsername();
				model.addAttribute("username", username);
//				System.out.println("authenticated user :"+username);

			}
			
			
			

	        
//	        and if user from database will be direct returned with user home page

	        // Common logic for both types of users
	        return "user_Home";
	    }
	

	  
	
	
}

