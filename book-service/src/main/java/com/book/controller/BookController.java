package com.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book.entity.BookEntity;
import com.book.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("addBook")
	public ResponseEntity<BookEntity> addBook(@RequestBody BookEntity bookEntity) throws Exception  {
		bookEntity=bookService.addBook(bookEntity);
		return new ResponseEntity<BookEntity>(bookEntity, HttpStatus.OK);
	}

	@PutMapping("updateBook/{bookId}")
	public ResponseEntity<BookEntity> updateBook(@RequestBody BookEntity bookEntityUpdate,
			@PathVariable Integer bookId) throws Exception {
		BookEntity bookEntity = bookService.updateBook(bookEntityUpdate, bookId);
		return new ResponseEntity<BookEntity>(bookEntity, HttpStatus.OK);
	}

	@DeleteMapping("deleteBook/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer bookId) throws Exception  {
		bookService.deleteBook(bookId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("findBookById/{bookId}")
	public ResponseEntity<BookEntity> findBookById(@PathVariable Integer bookId) throws Exception  {
		BookEntity bookEntity = bookService.findBookById(bookId);
		return new ResponseEntity<BookEntity>(bookEntity, HttpStatus.OK);
	}

	@GetMapping("findBookByPrice/{price}")
	public ResponseEntity<List<BookEntity>> findBookByPrice(@PathVariable Double price) throws Exception  {
		List<BookEntity> bookEntityList = bookService.findBookByPrice(price);
		return new ResponseEntity<List<BookEntity>>(bookEntityList, HttpStatus.OK);
	}
}
