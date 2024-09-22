package com.SpringBootJwtAuthentication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.SpringBootJwtAuthentication.Entity.Users;
import com.SpringBootJwtAuthentication.Repository.Repositories;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	Repositories repositories;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/save")
	public Users CreateUser(@RequestBody Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return 	 repositories.save(user);
		


}

}
