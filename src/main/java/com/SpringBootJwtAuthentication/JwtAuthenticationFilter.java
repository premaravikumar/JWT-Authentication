package com.SpringBootJwtAuthentication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SpringBootJwtAuthentication.Service.CustomerDetailService;
import com.SpringBootJwtAuthentication.WebToken.jwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	
	@Autowired
	private jwtUtil jwtUtil;
	
	@Autowired
	private CustomerDetailService customerDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwt=authHeader.substring(7);
		String username=jwtUtil.extractUsername(jwt);
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
		UserDetails userDetails	=customerDetailService.loadUserByUsername(username);
		if(userDetails!=null && jwtUtil.isTokenValid(jwt)) {
			UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken
					(username, userDetails.getPassword(),userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		}
		}
		filterChain.doFilter(request, response);
		
		
	}

}
