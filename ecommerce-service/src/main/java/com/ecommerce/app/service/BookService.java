package com.ecommerce.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.app.conf.BookDto;

@Service
public interface BookService {

	ResponseEntity<List<BookDto>> getAllBooks();

}
