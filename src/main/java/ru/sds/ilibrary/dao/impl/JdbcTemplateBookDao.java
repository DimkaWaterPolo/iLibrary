package ru.sds.ilibrary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.model.entity.Author;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.Publisher;

@Component(value = "jdbcTemplateBookDao")
public class JdbcTemplateBookDao implements IBookDao {
	
	@Autowired
	@Qualifier(value= "jdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier(value="transactionManager")
	private DataSourceTransactionManager transactionManager;//менеджер транзакций
	
	
	public JdbcTemplateBookDao() {
	}
	

	public JdbcTemplateBookDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Book> getListBooks() throws SQLException {
		String sql = "SELECT * FROM library.Book";
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		return jdbcTemplate.query(sql, params, new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return new Book(rs.getInt("id"), 
									rs.getString("bookName"), 
										getAuthor(rs.getInt("author_id")), 
											getGenre(rs.getInt("genre_id")), 
												getPublisher(rs.getInt("publisher_id")));
			}
		});
	}

	@Override
	public Book getBookByName(String bookName) throws SQLException {
		
		Book book2 = null;
		try {
			String sql = "SELECT * FROM library.Book WHERE bookName = :bookName";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("bookName", bookName);
			
			 book2 = jdbcTemplate.queryForObject(sql, params, new RowMapper<Book>() {

				@Override
				public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Book(rs.getInt("id"), 
										rs.getString("bookName"), 
											getAuthor(rs.getInt("author_id")), 
												getGenre(rs.getInt("genre_id")),
													getPublisher(rs.getInt("publisher_id")));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(book2==null) {
			return null;
		}else 
			return book2;
	}
	
	public Author getAuthor(int id) {
		String sql = "SELECT * FROM library.Author WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		return jdbcTemplate.queryForObject(sql, params, new RowMapper<Author>() {

			@Override
			public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Author(rs.getInt("id"), rs.getString("name"));
			}
		});
	}
	
	public Genre getGenre(int id) {
		String sql = "SELECT * FROM library.Genre WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		return jdbcTemplate.queryForObject(sql, params, new RowMapper<Genre>() {

			@Override
			public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Genre(rs.getInt("id"), rs.getString("genreName"));
			}
		});
	}
	
	public Publisher getPublisher(int id) {
		String sql = "SELECT * FROM library.Publisher WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		return jdbcTemplate.queryForObject(sql, params, new RowMapper<Publisher>() {

			@Override
			public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Publisher(rs.getInt("id"), rs.getString("publisherName"));
			}
		});
	}

	@Override
	public List<Book> getListBookByGanre(Genre genre) {
		List<Book> books = new ArrayList<Book>();
		try {
			String sql ="SELECT * FROM library.Book WHERE genre_id = :genre_id";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("genre_id", getIdGenreByName(genre.getGenreName()));
			
			books = jdbcTemplate.query(sql, params, new RowMapper<Book>() {

				@Override
				public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					return new Book(rs.getInt("id"), 
										rs.getString("bookName"), 
											getAuthor(rs.getInt("author_id")), 
												getGenre(rs.getInt("genre_id")),
													getPublisher(rs.getInt("publisher_id")));
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
	return books;
		
	}
	
	public int getIdGenreByName(String genreName) {
		int row = 0;
		String sql ="SELECT id FROM library.Genre WHERE genreName = :genreName";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("genreName", genreName);
		System.out.println(genreName);
		row= jdbcTemplate.queryForObject(sql, params, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt("id");
			}
		});
		System.out.println(row);
		return row;
	}

	@Override
	public boolean addBook(Book book) {
		TransactionStatus ts = null;//наша транзакция
		
		try {
			Book book2 = getBookByName(book.getBookName());
			if(book2!=null) {
				return false;
			}
			
			//начинаем транзакцию
			DefaultTransactionDefinition td = new DefaultTransactionDefinition();//подготовливаем транзакцию(свойства)
			td.setName("addBook");
			td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			ts = transactionManager.getTransaction(td);// передаем нашей транзакции свойства
			
			
			Author author = getAuthor(book.getAuthor().getName());
			if(author!=null) {
				book.setAuthor(author);
			}else {
				if(!addAuthor(book)) {
					transactionManager.rollback(ts);
					return false;
				}	
			}
			
			Genre genre = getGenre(book.getGenre().getGenreName());
			if(genre!=null) {
				book.setGenre(genre);
			}else {
				if(!addGenre(book)) {
					transactionManager.rollback(ts);
					return false;
				}	
				
			}
			
			Publisher publisher = getPublisher(book.getPublisher().getPublisherName());
			if(publisher!=null) {
				book.setPublisher(publisher);
			}else {
				if(!addPublisher(book)) {
					transactionManager.rollback(ts);
					return false;
				}	
				
			}
			
			String sql3 = "INSERT INTO library.book (genre_id, publisher_id, bookName, author_id) values (:genre_id, :publisher_id, :bookName, :author_id)";
			MapSqlParameterSource params3 = new MapSqlParameterSource();
			params3.addValue("genre_id", book.getGenre().getId());
			params3.addValue("publisher_id", book.getPublisher().getId());
			params3.addValue("bookName", book.getBookName());
			params3.addValue("author_id", book.getAuthor().getId());
			
			if(jdbcTemplate.update(sql3, params3)==0) {
				transactionManager.rollback(ts);
				return false;
			}else {
				transactionManager.commit(ts);
			return true;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			transactionManager.rollback(ts);
			return false;
		}
		
	}
	
	//OPERATION WITH AUTHOR
	public boolean addAuthor(Book book) {
		String sql = "INSERT into library.author (name) values (:name)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", book.getAuthor().getName());
		KeyHolder key = new GeneratedKeyHolder();
		if(jdbcTemplate.update(sql, params, key)>0) {
			book.getAuthor().setId(key.getKey().intValue());
			return true;
		}
		
		return false;
	}
	public Author getAuthor(String nameAuthor) {
		try {
			String sql= "select * from library.author where name= :name";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", nameAuthor);
			
			return jdbcTemplate.queryForObject(sql, params, new RowMapper<Author>() {

				@Override
				public Author mapRow(ResultSet rs, int rowNum) throws SQLException {	
					return new Author(rs.getInt("id"), rs.getString("name"));
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	//OPERATION WITH GENRE
	public boolean addGenre(Book book) {
		String sql = "INSERT INTO library.genre (genreName) values (:genreName)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("genreName", book.getGenre().getGenreName());
		KeyHolder key = new GeneratedKeyHolder();
		if(jdbcTemplate.update(sql, params, key)>0) {
			book.getGenre().setId(key.getKey().intValue());
			return true;
		}
		return false;
	}
	
	public Genre getGenre(String nameGenre) {
		try {
			String sql= "select * from library.genre where genreName= :genreName";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("genreName", nameGenre);
			
			return jdbcTemplate.queryForObject(sql, params, new RowMapper<Genre>() {

				@Override
				public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Genre(rs.getInt("id"), rs.getString("genreName"));
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	
	//OPERATION WITH PUBLISHER
	public boolean addPublisher(Book book) {
		String sql = "INSERT INTO library.publisher (publisherName) values (:publisherName)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("publisherName", book.getPublisher().getPublisherName());
		KeyHolder key = new GeneratedKeyHolder();
		if(jdbcTemplate.update(sql, params, key)>0) {
			book.getPublisher().setId(key.getKey().intValue());
			return true;
		}
		return false;
	}
	public Publisher getPublisher(String namePublisher) {
		try {
			String sql= "select * from library.publisher where publisherName= :publisherName";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("publisherName", namePublisher);
			
			return jdbcTemplate.queryForObject(sql, params, new RowMapper<Publisher>() {

				@Override
				public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Publisher(rs.getInt("id"), rs.getString("publisherName"));
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean deleteBook(Book book) {
		String sql = "DELETE FROM library.book WHERE bookName =:bookName";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("bookName", book.getBookName());
		
		
		return (jdbcTemplate.update(sql, params)>0);
	}

	@Override
	public boolean deleteTabels() {
		String sql = "DELETE FROM library.Book";
		MapSqlParameterSource params = new MapSqlParameterSource();
		jdbcTemplate.update(sql, params);
		
		String sql2 = "DELETE FROM library.Author";
		MapSqlParameterSource params2 = new MapSqlParameterSource();
		jdbcTemplate.update(sql2, params2);
		
		String sql3 = "DELETE FROM library.Genre";
		MapSqlParameterSource params3 = new MapSqlParameterSource();
		jdbcTemplate.update(sql3, params3);
		
		String sql4 = "DELETE FROM library.Publisher";
		MapSqlParameterSource params4 = new MapSqlParameterSource();
		jdbcTemplate.update(sql4, params4);
			
		return true;
	}


	@Override
	public List<Genre> getListGenre() {
		// TODO Auto-generated method stub
		return null;
	}

}
