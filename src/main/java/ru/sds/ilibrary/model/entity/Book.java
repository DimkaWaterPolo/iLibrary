package ru.sds.ilibrary.model.entity;


public class Book {

	private int id;
	private String bookName;
	private Author author;
	private Genre genre;
	private Publisher publisher;
	
	
	
	public Book() {
		super();
	}
		
	public Book(int id, String bookName) {
		super();
		this.id = id;
		this.bookName = bookName;
	}

	public Book(int id, String bookName, Author author, Genre genre, Publisher publisher) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.genre = genre;
		this.publisher = publisher;
	}
	
	

	public Book(String bookName, Author author, Genre genre, Publisher publisher) {
		super();
		this.bookName = bookName;
		this.author = author;
		this.genre = genre;
		this.publisher = publisher;
	}

	public String getBookName() {
		return bookName;
	}
	public void setBookName(String name) {
		this.bookName = name;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", author=" + author + ", genre=" + genre + ", publisher="
				+ publisher + "]";
	}
	
	
	
	
}
