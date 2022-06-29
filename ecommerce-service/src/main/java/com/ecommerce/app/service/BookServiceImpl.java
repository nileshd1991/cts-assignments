package com.ecommerce.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.app.conf.BookDto;

@Service
public class BookServiceImpl implements BookService{

	private static final String BOOK_SERVICE_URL="http://localhost:8085/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseEntity<List<BookDto>> getAllBooks(){
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entity=new HttpEntity<>(headers);
		return restTemplate.exchange(BOOK_SERVICE_URL+"getAllBooks", HttpMethod.GET,entity ,new ParameterizedTypeReference<List<BookDto>>() {
		});
		
	}
}
