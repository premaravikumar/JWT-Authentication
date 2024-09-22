package com.SpringBootJwtAuthentication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.SpringBootJwtAuthentication.JwtAuthenticationFilter;
import com.SpringBootJwtAuthentication.Service.CustomerDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired 
	private CustomerDetailService customerDetailService;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return customerDetailService;
	}
	
	@Bean
	 BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		return provider;
	}
	
	@Bean
	 public AuthenticationManager authenticationManager() {
		 return new ProviderManager(authenticationProvider());
	 }
	
	@Bean
	public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authz -> {
					authz.requestMatchers("/save","/home","/authenticate").permitAll();
					authz.requestMatchers("/user/**").hasRole("USER");
					authz.requestMatchers("/admin/**").hasRole("ADMIN");
					authz.anyRequest().authenticated();
				})
				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

}
