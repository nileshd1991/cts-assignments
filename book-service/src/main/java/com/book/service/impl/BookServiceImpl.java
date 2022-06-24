package com.book.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.book.entity.BookEntity;
import com.book.exceptions.BookApiException;
import com.book.service.BookRepository;
import com.book.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Override
	public BookEntity addBook(BookEntity bookEntity) throws Exception {
		return bookRepo.save(bookEntity);
	}

	@Override
	public BookEntity updateBook(BookEntity bookEntity, Integer bookId) throws Exception {
		Optional<BookEntity> opt = bookRepo.findById(bookId);
		if (opt.isPresent()) {
			bookEntity.setBookId(opt.get().getBookId());
			bookRepo.save(bookEntity);
			return bookEntity;
		} else {
			throw new BookApiException("Invalid book Id", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteBook(Integer bookId) throws Exception  {
		Optional<BookEntity> opt = bookRepo.findById(bookId);
		if(opt.isPresent()) {
			bookRepo.deleteById(bookId);
		}
		else{
			throw new BookApiException("Invalid book Id", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public BookEntity findBookById(Integer bookId) throws Exception  {
		Optional<BookEntity> opt = bookRepo.findById(bookId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new BookApiException("Invalid book Id", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<BookEntity> findBookByPrice(Double price) throws Exception  {
		List<BookEntity> list = bookRepo.findAllByPrice(price);
		return list;
	}

}
