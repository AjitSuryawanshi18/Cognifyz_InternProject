//Blog Related mappings 
package com.example.internproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.internproject.Repos.BlogRepository;
import com.example.internproject.Repos.MyUserRepository;
import com.example.internproject.model.Blog;
import com.example.internproject.model.MyUser;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

 @Autowired
 private MyUserRepository myUserRepository;
 @Autowired
 private BlogRepository blogRepository;

 @GetMapping("/viewAllBlogs")
 public String index(Model model) {
	 
	 //it is for checking error handling
//String s = null;
//char s1 = s.charAt(0);
	 
     List<Blog> blogs = blogRepository.findAll();
     model.addAttribute("blogs", blogs);

     return "/Blog_App/viewAllBlogs";
 }

 //this mapping for read all blog 
 @GetMapping("/viewAllBlogs/read/{id}")
 public String viewBlog(@PathVariable Long id, Model model) {
     Blog blog = blogRepository.findById(id)
                               .orElseThrow(() -> new IllegalArgumentException("Invalid blog id"));
     model.addAttribute("blog", blog);
     return "/Blog_App/viewBlog";
 }

 
 
 // Implement other CRUD operations here
 @GetMapping("/user/addNewBlog")
 public String addBlogForm(Model model) {
     model.addAttribute("blog", new Blog());
     return "/Blog_App/addNewBlog";
 }

 
 
 
 // this mappings is for adding blogs and then submit it and redirected to the particular user All blogs page
 @PostMapping("/user/submit/myAllBlogs")
 public String addBlog(@ModelAttribute Blog blog, Authentication authentication, Model model) {
     blog.setCreatedAt(LocalDateTime.now());
     
   //userEmail Variable for fetching Authenticated User
   	 String userEmail;
   	 
   	 if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            // User logged in via Google OAuth 
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
             userEmail = oidcUser.getEmail();
   	 }else if (authentication.getPrincipal() instanceof OAuth2User) {
//		 System.out.println("coming in github myallogs");
	   	    // User logged in via GitHub OAuth
		 OAuth2User oauthToken = (OAuth2User) authentication.getPrincipal();
	   	String gitlogin = oauthToken.getAttribute("login");
	   	String login = gitlogin+"@gmail.com";
	   	userEmail =login.toLowerCase();
//	   	System.out.println("email address of the github logged user from myAllBlogs : "+userEmail);
	   	} else {
//   		 Retrieve the email of the authenticated user it is an normal registered user
   	     userEmail = authentication.getName();
   	}
   	 
 
     // Retrieve the user from the database based on the email
     Optional<MyUser> userOptional = myUserRepository.findByEmail(userEmail);
     if (userOptional.isPresent()) {
         MyUser currentUser = userOptional.get();
         // Set the author of the blog to the current user
         blog.setAuthor(currentUser);
         blogRepository.save(blog);
         // Redirect the user to the page displaying all their blogs
         return "redirect:/user/myAllBlogs";
     } else {
         // Handle the case where the user is not found
         model.addAttribute("errorMsg", "User not found.");
         return "/Blog_App/errorPage"; // You can customize this error page
     }
 }

 
 
 
 
 //this for checking Particular user blogs directly from user home page
 @GetMapping("/user/myAllBlogs")
 public String myAllBlogs( Authentication authentication,Model model,HttpServletRequest request) {

	 //userEmail Variable for fetching Authenticated User
	 String userEmail;
	 
	 if (authentication.getPrincipal() instanceof DefaultOidcUser) {
         // User logged in via Google OAuth2 
         DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
          userEmail = oidcUser.getEmail();
	 }else if (authentication.getPrincipal() instanceof OAuth2User) {
		 System.out.println("coming in github myallogs");
	   	    // User logged in via GitHub OAuth2
		 OAuth2User oauthToken = (OAuth2User) authentication.getPrincipal();
	   	   String gitlogin = oauthToken.getAttribute("login");
	   	String login = gitlogin+"@gmail.com";
	   	userEmail =login.toLowerCase();
	   	System.out.println("email adrees of the github logged user from myAllBlogs : "+userEmail);
	   	}else { 
//		 Retrieve the email of the authenticated user it is an normal registered user i.e. from database
	     userEmail = authentication.getName();
		
	   	}

	 // Retrieve the user from the database based on the email
     Optional<MyUser> userOptional = myUserRepository.findByEmail(userEmail);
     if (userOptional.isPresent()) {
         MyUser currentUser = userOptional.get();
         
         
         List<Blog> myBlogs = currentUser.getBlogs();
         model.addAttribute("myBlogs", myBlogs);
         
         if (myBlogs.isEmpty()) {
             model.addAttribute("myAllBlogsMsg", "User has No Blogs yet.");
             
		}
         
         return "/Blog_App/myAllBlogs";
     } else {
         // Handled the case where the user is not found
         model.addAttribute("errorMsg", "User not found.");
         return "/Blog_App/errorPage"; 
     }
     
 }
 
 
 
 
 
 //mapping for edit page render
 @GetMapping("/user/edit/{id}")
 public String editBlogForm(@PathVariable Long id, Model model) {
     Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid blog id"));
     model.addAttribute("blog", blog);
     return "/Blog_App/edit";
 }

 
 
 //update mapping after clicking submit from edit page
 @PostMapping("/user/update/{id}")
 public String updateBlog(@PathVariable Long id, @ModelAttribute Blog updatedBlog) {
     Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid blog id"));
     blog.setTitle(updatedBlog.getTitle());
     blog.setContent(updatedBlog.getContent());
     blogRepository.save(blog);
     return "redirect:/user/myAllBlogs";
 }
 
 
 
 
 

 // delete blog mapping
 @GetMapping("/user/delete/{id}")
 public String deleteBlog(@PathVariable Long id) {
     Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid blog id"));
     blogRepository.delete(blog);
     return "redirect:/user/myAllBlogs";
 }
}

