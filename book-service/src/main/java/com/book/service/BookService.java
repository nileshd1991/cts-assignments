package com.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.book.entity.BookEntity;

@Service
public interface BookService {

	BookEntity addBook(BookEntity bookEntity) throws Exception ;

	BookEntity updateBook(BookEntity bookEntity, Integer bookId) throws Exception;

	void deleteBook(Integer bookId) throws Exception ;

	BookEntity findBookById(Integer bookId) throws Exception ;

	List<BookEntity> findBookByPrice(Double price) throws Exception ;

	List<BookEntity> getAllBooks();

}
