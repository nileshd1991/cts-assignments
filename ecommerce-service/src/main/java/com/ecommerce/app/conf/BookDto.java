package com.ecommerce.app.conf;

public class BookDto {
	private Integer bookId;
	private String title;
	private String author;
	private Double price;

	public BookDto() {
		// TODO Auto-generated constructor stub
	}

	public BookDto(Integer bookId, String title, String author, Double price) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.price = price;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
