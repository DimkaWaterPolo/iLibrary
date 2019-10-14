package ru.sds.ilibrary.service.impl;

import ru.sds.ilibrary.service.interfaces.ISecurityService;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.User;
import ru.sds.ilibrary.service.interfaces.IBookService;

@Component(value="serviceController")
public class ServiceController implements IBookService, ISecurityService {
	
	@Autowired
	private ISecurityService securityService;
	@Autowired
	private IBookService bookService;
	
	Logger logger = Logger.getLogger(ServiceController.class);

	public ServiceController() {
		super();
	}

	public ServiceController(ISecurityService securityService,IBookService bookService) {
		super();
		this.securityService = securityService;
		this.bookService = bookService;
	}
	

	public ISecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}

	public IBookService getBookService() {
		return bookService;
	}

	public void setBookService(IBookService bookService) {
		this.bookService = bookService;
	}

	public boolean login(User user) {
		return securityService.login(user);
	}

	public boolean logout(User user) {
		return securityService.logout(user);
	}

	public List<Book> getListBooks() throws SQLException {
		return bookService.getListBooks();
	}

	public Book getBookByName(String bookName) throws SQLException {
		return bookService.getBookByName(bookName);
	}

	public List<Book> getListBookByGanre(Genre genre) {
		return bookService.getListBookByGanre(genre);
	}

	public boolean addBook(Book book) {
		logger.debug("Вход в метод addBook,класса ServiceController ");
		logger.debug("Вызов метода addBook, класса BookService");
		logger.debug("Выход из метода addBook, класса ServiceController ");
		return bookService.addBook(book);
	}

	public boolean deleteBook(Book book) {
		return bookService.deleteBook(book);
	}

	@Override
	public boolean addUser(User user) {
		return securityService.addUser(user);
	}

	@Override
	public boolean deleteUser(User user) {
		return securityService.deleteUser(user);
	}

	@Override
	public boolean deleteTables() {
		return bookService.deleteTables();
	}

	@Override
	public boolean deleteAllUser() {
		return securityService.deleteAllUser();
	}

	@Override
	public List<Genre> getListGenre() {
		return bookService.getListGenre();
	}

	@Override
	public User getUserByLogin(String login) {
		
		return securityService.getUserByLogin(login);
	}
	

}
