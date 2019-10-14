package ru.sds.ilibrary.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;


public interface IBookDao {

	public List<Book> getListBooks() throws SQLException;
	
	public Book getBookByName(String bookName) throws SQLException;
	
	public List<Book> getListBookByGanre(Genre genre);
	
	public List<Genre> getListGenre();
	
	public boolean addBook(Book book);
	
	public boolean deleteBook(Book book);
	
	public boolean deleteTabels();
}
