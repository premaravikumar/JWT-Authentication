package com.SpringBootJwtAuthentication.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringBootJwtAuthentication.Entity.Users;
import com.SpringBootJwtAuthentication.Repository.Repositories;

@Service
public class CustomerDetailService implements UserDetailsService {
	
	@Autowired
	private Repositories repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  Optional<Users> user=repo.findByUsername(username);
		  if(user.isPresent())
		  {
			  var obj=user.get();
			  return User.builder()
			  .username(obj.getUsername())
			  .password(obj.getPassword())
			  .roles(getRoles(obj))
			  .build();
		  }
		  else
		  {
			  throw new UsernameNotFoundException(username);
		  }
	}
	
	private String[] getRoles(Users user) {
		if(user.getRoles()==null) {
			return  new String[]{"USER"};
		}
		return  user.getRoles().split(",");
	}

}
