package com.flightapp.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.flightapp.admin.service.AdminUserService;
import com.flightapp.admin.service.exceptions.FlightApiException;
import com.flightapp.models.AdminUser;
import com.flightapp.models.Token;
import com.flightapp.security.config.JwtUtil;

@Service
public class AdminUserServiceImpl implements AdminUserService{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public ResponseEntity<Token> validateAdminUser(AdminUser body){
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(body.getUserName(), body.getPassword())
					);
		} catch (Exception e) {
			throw new FlightApiException("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
		
		String tokenStr = jwtUtil.generateToken(body.getUserName());
		Token token=new Token();
		token.setToken(tokenStr);
		return new ResponseEntity<Token>(token,HttpStatus.OK);
	}

}
