package ru.sds.ilibrary.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.model.entity.Author;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.Publisher;

//@Component(value="jdbcBookDao")
public class JdbcBookDao implements IBookDao {
	
	private final String SQL_SELECT_BOOKS_BY_NAME = "SELECT * FROM library.Book WHERE bookName = ?";
	private final String SQL_SELECT_AUTHOR_BY_NAME = "SELECT * FROM library.Author WHERE name = ?";
	private final String classDriverName = "com.mysql.jdbc.Driver";
	private final String jdbcUrl = "jdbc:mysql://localhost:3306/library";
	private final String SQL_SELECT_BOOKS_BY_GANRE = "SELECT * FROM library.Book WHERE genre_id = ?";
	private final String SQL_INSERT_INTO_BOOK = "INSERT INTO library.Book (genre_id, publisher_id, bookName, author_id) VALUES (?,?,?,?)";
	private final String SQL_DELETE_BOOK = "DELETE FROM library.Book WHERE id = ?";
	private final String SQL_INSERT_AUTHOR = "INSERT INTO library.Author(name) VALUES (?)";
	private final String SQL_INSERT_GENRE = "INSERT INTO library.Genre(genreName) VALUES (?)";
	private final String SQL_INSERT_PUBLISHER = "INSERT INTO library.Publisher(publisherName) VALUES (?)";
	private final String SQL_SELECT_GENRE_BY_NAME = "SELECT * FROM library.Genre WHERE genreName = ?";
	private final String SQL_SELECT_PUBLISHER_BY_NAME = "SELECT * FROM library.Publisher WHERE publisherName = ?";
	//private final String SQL_DELETE_AUTHOR = "DELETE FROM library.Author WHERE id = ?";
	//private final String SQL_DELETE_GENRE ="DELETE FROM library.Genre WHERE id = ?";
	//private final String SQL_DELETE_PUBLISHER = "DELETE FROM library.Publisher WHERE id = ?";
	
	public JdbcBookDao() {
		try {
			Class.forName(classDriverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Book> getListBooks() throws SQLException  {
		List<Book> books = new ArrayList<Book>();
		
	
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM library.Book");
				) {
				while(resultSet.next()) {
				
					books.add(new Book(resultSet.getInt("id"), resultSet.getString("bookName"), 
								getAuthor(resultSet.getInt("author_id")), 
									getGenre(resultSet.getInt("genre_id")),
										getPublisher(resultSet.getInt("publisher_id"))));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		return books;
	}

	public Book getBookByName(String bookName) throws SQLException {
		
		Book book = null;
		
		try (Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_NAME);
			) {			
			statement.setString(1, bookName);
			try(ResultSet resultSet = statement.executeQuery()) {		
				while(resultSet.next()) {			
					book = new Book(resultSet.getInt("id"), resultSet.getString("bookName"),
									 getAuthor(resultSet.getInt("author_id")), 
									 	getGenre(resultSet.getInt("genre_id")), 
									 		getPublisher(resultSet.getInt("publisher_id")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 					
		return book;
	}

	public List<Book> getListBookByGanre(Genre genre) {
		List<Book> books = new ArrayList<Book>();
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BOOKS_BY_GANRE);
				) {
			
			statement.setInt(1, genre.getId());
			
			try(ResultSet resultSet = statement.executeQuery();){
			
			while(resultSet.next()) {
				books.add(new Book(resultSet.getInt("id"), resultSet.getString("bookName"),
									getAuthor(resultSet.getInt("author_id")),
										getGenre(resultSet.getInt("genre_id")), 
											getPublisher(resultSet.getInt("publisher_id"))));
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return books;
	}
	
	

	public boolean addBook(Book book) {
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");) {
			
			Book book2 = getBookByName(book.getBookName());
			if(book2 != null) {
				return false;
			}
			
			connection.setAutoCommit(false);
			
			//AUTHOR
			if (book.getAuthor() == null) {
				connection.rollback();
				return false;
			}
			Author author = getAuthorByName(book.getAuthor().getName());
			if (author == null) {
				//addAuthor(book.getAuthor());
				if (!addAuthor(book.getAuthor())) {
					connection.rollback();
					return false;
				}
			} else {
				book.getAuthor().setId(author.getId());
			}
			
			//GENRE
			if(book.getGenre() == null) {
				connection.rollback();
				return false;
			}
			Genre genre = getGenreByName(book.getGenre().getGenreName());
			if(genre == null) {
				//addGenre(book.getGenre());
				if(!addGenre(book.getGenre())){
					connection.rollback();
					return false;
				}
			}else {
				book.getGenre().setId(genre.getId());
			}
			
			//PUBLISHER
			if(book.getPublisher() == null) {
				connection.rollback();
				return false;
			}
			Publisher publisher = getPublisherByName(book.getPublisher().getPublisherName());
			if(publisher == null) {
				//addPublisher(book.getPublisher());
				if(!addPublisher(book.getPublisher())) {
					connection.rollback();
					return false;
				}
			}else {
				book.getPublisher().setId(publisher.getId());
			}
			
						
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_BOOK, Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, book.getGenre().getId());
			statement.setInt(2, book.getPublisher().getId());
			statement.setString(3, book.getBookName());
			statement.setInt(4, book.getAuthor().getId());
			if (statement.executeUpdate()==0) {
    			return false;
    		}
    		try(ResultSet resultSet = statement.getGeneratedKeys()){
    			if(!resultSet.next()) {
    				connection.rollback();
    				return false;
    			}else {
    				book.setId(resultSet.getInt(1));
    			}
    		}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}

	public boolean deleteBook(Book book) {
		
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BOOK);
			statement.setInt(1, book.getId());
			//statement.executeUpdate();
			if(statement.executeUpdate()==0) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Author getAuthor(int author_id){
		
		Author author =null;
		
		try {

			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			
			String sql1 = "SELECT * FROM library.Author WHERE id = ?";
			PreparedStatement statement1 = connection.prepareStatement(sql1);
			statement1.setInt(1, author_id);
			ResultSet resultSet1 =  statement1.executeQuery();
			
			if (resultSet1.next()) {
				author = new Author(resultSet1.getInt("id"), resultSet1.getString("name"));
			}
		}catch (SQLException e) {
			return null;
		}
		return author;
	}
	
	public Genre getGenre(int genre_id){

		Genre genre = null;
		
		try {
			
			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			
			String sql2 = "SELECT * FROM library.Genre WHERE id = ?";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setInt(1, genre_id);
			ResultSet resultSet2 = statement2.executeQuery();;
			
			if (resultSet2.next()) {
				genre = new Genre(resultSet2.getInt("id"), resultSet2.getString("genreName"));
			
		}
			}catch (SQLException e) {
			return null;
		}
		return genre;
	}
	
	public Publisher getPublisher(int publisher_id){
	
		Publisher publisher = null;
		
		try {

			Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			
			String sql3 = "SELECT * FROM library.Publisher WHERE id = ?";
			PreparedStatement statement3 = connection.prepareStatement(sql3);
			statement3.setInt(1, publisher_id);
			ResultSet resultSet3 = statement3.executeQuery();
			
			if (resultSet3.next()) {
				publisher = new Publisher(resultSet3.getInt("id"), resultSet3.getString("publisherName"));
			}
			
			}catch (SQLException e) {
			return null;
		}
		return publisher;
	}
	
	public Author getAuthorByName(String name) {
		Author author = null;
		
		try (Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AUTHOR_BY_NAME);
			) {			
			statement.setString(1, name);
			try(ResultSet resultSet = statement.executeQuery()) {		
				if (resultSet.next()) {			
					author = new Author(resultSet.getInt("id"), resultSet.getString("name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 					
		return author;
	}
	
    public boolean addAuthor(Author author) {
    	try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS);	
				) {
    		statement.setString(1, author.getName());
    		if (statement.executeUpdate()==0) {
    			return false;
    		}
    		try(ResultSet resultSet = statement.getGeneratedKeys()){
    			if(!resultSet.next()) {
    				connection.rollback();
    				return false;
    			}else {
    				author.setId(resultSet.getInt(1));
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    public Genre getGenreByName(String name) {
		Genre genre = null;
		
		try (Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_GENRE_BY_NAME);
			) {			
			statement.setString(1, name);
			try(ResultSet resultSet = statement.executeQuery()) {		
				if (resultSet.next()) {			
					genre = new Genre(resultSet.getInt("id"), resultSet.getString("genreName"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 					
		return genre;
	}
    
    public boolean addGenre(Genre genre) {
    	try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GENRE, Statement.RETURN_GENERATED_KEYS);	
				) {
    		statement.setString(1, genre.getGenreName());
    		if (statement.executeUpdate()==0) {
    			return false;
    		}
    		try(ResultSet resultSet = statement.getGeneratedKeys()){
    			if(!resultSet.next()) {
    				connection.rollback();
    				return false;
    			}else {
    				genre.setId(resultSet.getInt(1));
    			}
    		}
	
    	} catch (Exception e) {
    		e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    public Publisher getPublisherByName(String name) {
		Publisher publisher = null;
		
		try (Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PUBLISHER_BY_NAME);
			) {			
			statement.setString(1, name);
			try(ResultSet resultSet = statement.executeQuery()) {		
				if (resultSet.next()) {			
					publisher = new Publisher(resultSet.getInt("id"), resultSet.getString("publisherName"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 					
		return publisher;
	}
    
    public boolean addPublisher(Publisher publisher) {
    	try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PUBLISHER, Statement.RETURN_GENERATED_KEYS);	
				) {
    		statement.setString(1, publisher.getPublisherName());
    		if (statement.executeUpdate()==0) {
    			return false;
    		}
    		try(ResultSet resultSet = statement.getGeneratedKeys()){
    			if(!resultSet.next()) {
    				connection.rollback();
    				return false;
    			}else {
    				publisher.setId(resultSet.getInt(1));
    			}
    		}
	
    	} catch (Exception e) {
    		e.printStackTrace();
			return false;
		}
    	return true;
    }

	@Override
	public boolean deleteTabels() {
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
	    		PreparedStatement statement1 = connection.prepareStatement("DELETE FROM library.Book");
				PreparedStatement statement2 = connection.prepareStatement("DELETE FROM library.Author");
				PreparedStatement statement3 = connection.prepareStatement("DELETE FROM library.Genre");
				PreparedStatement statement4 = connection.prepareStatement("DELETE FROM library.Publisher");
				){
			statement1.executeUpdate();
			statement2.executeUpdate();
			statement3.executeUpdate();
			statement4.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Genre> getListGenre() {
		List<Genre> genres = new ArrayList<Genre>();
		
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM library.Genre");
				) {
				while(resultSet.next()) {
				
					genres.add(new Genre(resultSet.getInt("id"),resultSet.getString("genreName")));
					
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		return genres;
	}

}
