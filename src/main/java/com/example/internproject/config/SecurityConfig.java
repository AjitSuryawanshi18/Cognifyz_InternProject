package com.example.internproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.ExceptionHandler;

//third step (we have to configure 4 steps here now ) : to work with security i.e. when we use security it will provide default security if we want to mold it as per our requirements then we have to follow this steps :

@Configuration
@EnableWebSecurity
@ComponentScan("com.example.internproject")
public class SecurityConfig {

//@Autowired
//AuthenticationSuccessHandler successHandler;

	@Autowired
	private MyUserDetailsService userDetailsService;

	// first step password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	// third step DaoAuthenticationProvider
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); // object created of
																								// DaoAuthenticationProvider
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);// set userDatailsService
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // set password

		return daoAuthenticationProvider; // return object

	}

	// forth step SecurityFilterChain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// actual role based authentication code starts here
		return http
				
				.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
				.authorizeHttpRequests(registry -> {
			registry.requestMatchers("/","/register","/terms_and_conditions").permitAll();
			registry.requestMatchers("/live_scores","/DonateUs","/create_donateus_order","/update_donate_order_toDB","/thankYouMsg").permitAll();
			registry.requestMatchers("/submit","/confirmation","/about","/contact","/viewAllBlogs","/index","/viewAllBlogs/read/**").permitAll();
			registry.requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll();
//			registry.requestMatchers("/user/**").hasRole("USER");
			registry.requestMatchers("/admin/**").hasRole("ADMIN");
			
				registry.anyRequest().authenticated();
			

		})
				.oauth2Login()
                .loginPage("/login")
//                .successHandler(new AuthenticationSuccessHandler())
                .successHandler(new CustomSuccessHandler())
                
                .and()
				
				.formLogin(AbstractAuthenticationFilterConfigurer -> {
				AbstractAuthenticationFilterConfigurer
				.loginPage("/login")
				.successHandler(new AuthenticationSuccessHandler()) // it will take user to their home page after successful login
//				.successHandler(new CustomSuccessHandler()) // it will take OAuth user to their home page after successful login
				.permitAll();
				})
				
				
				.build();


	}
	
	
	
	
}
