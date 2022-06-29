package com.ecommerce.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.conf.BookDto;
import com.ecommerce.app.service.BookService;

@RestController
public class BooksController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("getAllBooks")
	public ResponseEntity<List<BookDto>> getAllBooks() {
		return bookService.getAllBooks();
	}
}
