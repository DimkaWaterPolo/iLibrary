package ru.sds.ilibrary.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.model.entity.Author;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.Publisher;


public class StubBookDao implements IBookDao {

	private List<Book> books = new ArrayList<Book>();
	private Logger logger = Logger.getLogger(StubBookDao.class);
	
	public StubBookDao() {
		books.add(new Book(1, "bookName1", new Author(1, "authorName1")
				, new Genre(1, "genreName1"), new Publisher(1, "publisherName1")));
		books.add(new Book(2, "bookName2", new Author(2, "authorName2")
				, new Genre(2, "genreName2"), new Publisher(2, "publisherName2")));
		books.add(new Book(3, "bookName3", new Author(3, "authorName3")
				, new Genre(3, "genreName3"), new Publisher(3, "publisherName3")));
		books.add(new Book(4, "bookName4", new Author(4, "authorName4")
				, new Genre(4, "genreName4"), new Publisher(4, "publisherName4")));
	}

	public List<Book> getListBooks() {
		logger.debug("Вызов метода getListBooks");
		return books;
	}

	public Book getBookByName(String bookName) {
		logger.debug("Зашли в метод getBookByName");
		logger.debug("Выполнения тела метода с входными параметрами: " + bookName);
		
		try {
			for(Book book: books) {
				if(book.getBookName().equals(bookName)) {
					logger.debug("Метод выполнен. Выходные парметры" + book);
					logger.debug("Выход из метода");
					return book;
				}
			}
			
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		logger.debug("Метод выполнен. Выходные парметры = null" );
		logger.debug("Выход из метода");
		
		return null;
	}

	public List<Book> getListBookByGanre(Genre genre) {
		logger.debug("Зашли в метод getListBookByGanre");
		logger.debug("Выполнения тела метода с входными параметрами: " + genre);
		
		List<Book> tempBooks = new ArrayList<Book>();
		
		try {
			for(Book book: books) {
				if(book.getGenre().getId() == genre.getId()) {
					tempBooks.add(book);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		logger.debug("Метод выполнен. Выходные парметры: " + tempBooks.toString() );
		logger.debug("Выход из метода");
		
		return tempBooks;
	}

	public boolean addBook(Book book) {
		logger.debug("Зашли в метод addBook, класса StubBookDao");
		logger.debug("Выполнения тела метода с входными параметрами: " + book);
		
		try {
			if(!books.contains(book)) {
				logger.debug("Метод выполнен. Выходные парметры: try " );
				logger.debug("Выход из метода");
				return books.add(book);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		logger.debug("Метод выполнен. Выходные парметры: false" );
		logger.debug("Выход из метода");
		
		return false;
	}
			
	public boolean deleteBook(Book book) {	
		return books.remove(book);
	}

	@Override
	public boolean deleteTabels() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Genre> getListGenre() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
