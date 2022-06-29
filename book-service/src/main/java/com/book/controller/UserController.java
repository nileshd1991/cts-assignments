package com.book.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin // postman, rest client, postwoman -> 
public class UserController {

	@GetMapping("/greet")
	public String greet() {
		return "Welcome user";
	}
	@GetMapping("/greet/{username}")
	public String greet(@PathVariable String username) {
		return "Welcome "+username;
	}
	
	@GetMapping("/admin")
	public String onlyForAdmin() {
		return "Welcome admin";
	}
	
	@GetMapping("/manager")
	public String onlyForManager() {
		return "Welcome manager";
	}
	
	@GetMapping("/all")
	public String commonForAll() {
		return "Welcome all";
	}
	
	
	
}
