package com.price.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {
	
	@GetMapping("getPrice")
	public Double getPrice() {
		return Double.valueOf(25000d);
	}
}
