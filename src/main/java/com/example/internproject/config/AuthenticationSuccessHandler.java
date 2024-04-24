//database registered user success handler i.e. its handle the successHandler Path After registered user get authenticated.
package com.example.internproject.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		
		if(isAdmin) {
			setDefaultTargetUrl("/admin/home");
		}else {
			setDefaultTargetUrl("/user/home");			
		}
		
		
		

		super.onAuthenticationSuccess(request, response, authentication);
	}

	
	
}
