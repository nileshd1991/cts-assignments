package com.flightapp.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flightapp.schema.model.AdminUser;
import com.flightapp.schema.repository.AdminUserRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService{

	@Autowired
	private AdminUserRepository adminUserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminUser adminUser = adminUserRepo.findByUserName(username);
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(adminUser.getRole());
		List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
		list.add(authority);
		return new User(adminUser.getUserName(), adminUser.getPassword(), list);
	}

}
