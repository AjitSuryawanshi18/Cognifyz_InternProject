//OAuth2 User Success Handler i.e. it is handles the Success after OAuth2 User get AUthenticated
package com.example.internproject.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.internproject.Repos.MyUserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	MyUserRepository userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;

		if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
			String username = userDetails.getAttribute("email") != null ? userDetails.getAttribute("email")
					: userDetails.getAttribute("login") + "@gmail.com";
			// I'll configured it to handle new user when it Come From OAuth2 But REpo Issue i have configured it in respective Controller.
        
		}
		redirectUrl = "/user/home";

		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

}
