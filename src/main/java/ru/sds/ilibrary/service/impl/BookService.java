package ru.sds.ilibrary.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IAuthorDao;
import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.dao.interfaces.IGenreDao;
import ru.sds.ilibrary.dao.interfaces.IPublisherDao;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.service.interfaces.IBookService;

@Component
public class BookService implements IBookService {
	
	Logger logger = Logger.getLogger(BookService.class);
	
	@Autowired
	private IBookDao bookDao;
	
	private IAuthorDao authorDao;

	private IGenreDao genreDao;
	private IPublisherDao publisherDao;
		
	public BookService() {
	}
	
	public BookService(IBookDao bookDao) {
		super();
		this.bookDao = bookDao;
	}



	public BookService(IBookDao bookDao, IAuthorDao authorDao, IGenreDao genreDao, IPublisherDao publisherDao)
	{
		super();
		this.bookDao = bookDao;
		this.authorDao = authorDao;
		this.genreDao = genreDao;
		this.publisherDao = publisherDao;

	}

	public IBookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(IBookDao bookDao) {
		this.bookDao = bookDao;
	}

	public IAuthorDao getAuthorDao() {
		return authorDao;
	}

	public void setAuthorDao(IAuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public IGenreDao getGenreDao() {
		return genreDao;
	}

	public void setGenreDao(IGenreDao genreDao) {
		this.genreDao = genreDao;
	}

	public IPublisherDao getPublisherDao() {
		return publisherDao;
	}

	public void setPublisherDao(IPublisherDao publisherDao) {
		this.publisherDao = publisherDao;
	}


	public List<Book> getListBooks() throws SQLException {
		return bookDao.getListBooks();
	}

	public Book getBookByName(String bookName) throws SQLException {
		return bookDao.getBookByName(bookName);
	}

	public List<Book> getListBookByGanre(Genre genre) {
		return bookDao.getListBookByGanre(genre);
	}

	public boolean addBook(Book book) {
		logger.debug("Вход в метод addBook, класса BookService");
		logger.debug("Вызов метода addBook, класса BookDao ");
		logger.debug("Выход из метода addBook, класса BookService");
		return bookDao.addBook(book);
		
	}

	public boolean deleteBook(Book book) {
		return bookDao.deleteBook(book);
		
	}

	@Override
	public boolean deleteTables() {
		return bookDao.deleteTabels();
	}

	@Override
	public List<Genre> getListGenre() {
		return bookDao.getListGenre();
	}
	
}
