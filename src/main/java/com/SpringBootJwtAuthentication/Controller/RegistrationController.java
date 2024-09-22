package com.SpringBootJwtAuthentication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBootJwtAuthentication.Service.CustomerDetailService;
import com.SpringBootJwtAuthentication.WebToken.LoginForm;
import com.SpringBootJwtAuthentication.WebToken.jwtUtil;

@RestController
public class RegistrationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private jwtUtil jwtUtil;
	
	@Autowired
	private CustomerDetailService customerDetailService;
	
	@GetMapping("/user/home")
	public String User() {
		return "you are User";
	}
	
	@GetMapping("/admin/home")
	public String admin() {
		return "you are valid Admin";
	}
	
	@GetMapping("/home")
	public String home() {
		return " Welcome Home";
	}
	
	@PostMapping("/authenticate")
	public String autenticationAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginForm.username(), loginForm.password())
				);
		if(authentication.isAuthenticated()) {
			return jwtUtil.GenerateToken(customerDetailService.loadUserByUsername(loginForm.username()));
			
		}else
		{
			throw new UsernameNotFoundException("invalid credentials");
		}
		
	}
}
