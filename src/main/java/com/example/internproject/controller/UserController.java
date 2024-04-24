package com.example.internproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


import com.example.internproject.Repos.MyUserRepository;
import com.example.internproject.model.MyUser;
import com.example.internproject.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@SessionAttributes("validatedFormData")
public class UserController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private MyUserRepository myUserRepository ;
	
	@Autowired
	private UserService userService;

	// when project runs on first page will show our project home page 
	@GetMapping("/")
	public String projectHome(Model model) {

//		model.addAttribute("formData", new MyUser());
		return "projectHome";

	}
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("formData", new MyUser());
		return "form";
		
	}

	//from confirmation page to the login page
		@GetMapping("/login")
		public String showLoginPage() {
			
		    return "Login"; 
		}

		 @GetMapping("/about")
		    public String about() {
		        return "about";
		    }

		    @GetMapping("/contact")
		    public String contact() {
		        return "contact";
		    }
		

		@GetMapping("/admin/home")
		public String AdminLogin(Model model) {
			
			 // Fetch users with role "USER"
	        List<MyUser> users = userService.getUsersWithRole("USER");

	        // Add users to the model
	        model.addAttribute("users", users);

			
			return "admin_Home";
		}
	
	
	
	
	
	
// this is for submit form data
	// and important one i am facing issues from 2 hrs and solved by just placing
	// annotations in proper sequence so look into it to set binding result after
	// model data
	// don't change the name 'formData' it should be the same else bugs thrown 
	@PostMapping("/submit")
	public String submitForm(@Valid @ModelAttribute("formData") MyUser User_formData, BindingResult result, Model model, HttpSession session) {

		// validation check 
		if (result.hasErrors()) {

			// If there are validation errors, adding them to the model
			model.addAttribute("errors", result.getAllErrors());

			// If there are validation errors,we can return to the form page and displaying
			// error which occurred and settled above
			return "form";
		}

		
		// this is for task 6 i.e. storing & retrieving form data 
//		User user = userRepository.save(User_formData);
		
		 String password=passwordEncoder.encode(User_formData.getPassword());
	         
		    User_formData.setRole("USER");
			User_formData.setPassword(password);
			 
	        
	     // Store email in session
	        session.setAttribute("email", User_formData.getEmail());
	      
		//saving form data after setting encrypting pass and setting role
		  myUserRepository.save(User_formData);
		  
		  


		// Store validated form data in session
		// Store validated data in temporary server-side storage. this is answer related to task 2
       //	session.setAttribute("validatedUser_formData", User_formData);

		return "redirect:/confirmation";

	}

	@GetMapping("/confirmation")
	public String showConfirmation(Model model, HttpSession session,@SessionAttribute("email") String email) {
		
		//here we are fetching user data from database and it will displayed in confirmation page to the user
		
		//step 1 get user by email 
		Optional<MyUser> user_fromDB = myUserRepository.findByEmail(email);
//		MyUser user_fromDB = myUserRepository.findByEmail(email);
		
		
		if (user_fromDB.isPresent()) {
		    MyUser user = user_fromDB.get();
		    // Now you we can access the required property
		     model.addAttribute("userData", user);

//			model.addAttribute("formDataToShowOnConfirmation", user_fromDB);
			return "confirmation";
		} else {
			// Handle case where session data is not found (e.g., session expired)
						model.addAttribute("errors", "Your Registration is Successfull but something went wrong! still you can login or you can please contact us on : xyz@gmail.com");

//						return "redirect:/";
						return "confirmation";
		}
		
		
		

		
		//this is the data for storing form data in session or in memory i.e. temporary storage as asked in task
		
		// In spring boot HttpSession it will store data in the server session direct(
		// tomcat, jetty server ) and if we are looking in javascript it will store in session in browser

		/*  
		// Retrieve validated form data from session
		User validatedUser = (User) session.getAttribute("validatedUser_formData");

		if (validatedUser == null) {
			// Handle case where session data is not found (e.g., session expired)
			model.addAttribute("errors", "data not found or session expired please register again");

//			return "redirect:/";
			return "confirmation";
		}
		model.addAttribute("formDataToShowOnConfirmation", validatedUser);

*/
		
		
//		return "confirmation";
	}

	
	
	
	
	
	
	// this is for terms and condition when user clicks on terms and condition user
	// can see pop up of organization terms and conditions.
	@GetMapping("/terms_and_conditions")
	public String terms_and_conditions(Model model) {

		model.addAttribute("User_formData", new MyUser());
		return "termsandcondition";

	}
	
	
	
	

}
