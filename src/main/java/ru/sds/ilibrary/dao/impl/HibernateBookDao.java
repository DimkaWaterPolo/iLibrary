package ru.sds.ilibrary.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.model.entity.Author;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.Publisher;
import ru.sds.ilibrary.model.entity.User;

@Component
public class HibernateBookDao implements IBookDao{
	
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	

	public HibernateBookDao() {
	}

	public List<Book> getListBooks() throws SQLException {
		List<Book> books ;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			books = session.createCriteria(Book.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return books;
	}
	
	
	public List<Author> getListAuthors() throws SQLException {
		List<Author> books ;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			books = session.createCriteria(Author.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return books;
	}

	
	public Book getBookByName(String bookName) throws SQLException {
		Book book = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			book = (Book)session.createCriteria(Book.class).add(Restrictions.eq("bookName", bookName)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return book;
	}

	
	public List<Book> getListBookByGanre(Genre genre) {
		List<Book> books = null;
		Session session = null;
		
		
		Genre genre1 = getGenre(genre.getId());
		try {
			session = sessionFactory.openSession();
			books = session.createCriteria(Book.class).add(Restrictions.eq("genre.id", genre.getId())).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return books;
	}

	
	public boolean addBook(Book book) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(book);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			return false;
		}
		finally {
			session.close();
		}
		return true;
	}

	
	public boolean deleteBook(Book book) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(book);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			return false;
		}
		finally {
			session.close();
		}
		return true;
	}

	
	public boolean deleteTabels() {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("delete from Book");
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			return false;
		}
		finally {
			session.close();
		}
		return true;
	}
	
	public Author getAuthor(int id) {
		Author author = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			author = (Author)session.createCriteria(Author.class).add(Restrictions.eq("id", id)).uniqueResult();
			//user= (User)session.createQuery("select from library.User where login = 'login'");
		} catch (Exception e) {
			return null;
		}finally {
			session.close();
		}
		
		return author;
	}
	
	public Genre getGenre(int id) {
		Genre genre = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			genre = (Genre)session.createCriteria(Genre.class).add(Restrictions.eq("id", id)).uniqueResult();
			//user= (User)session.createQuery("select from library.User where login = 'login'");
		} catch (Exception e) {
			return null;
		}finally {
			session.close();
		}
		
		return genre;
	}
	
	public Publisher getPublisher(int id) {
		Publisher publisher = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			publisher = (Publisher)session.createCriteria(Publisher.class).add(Restrictions.eq("id", id)).uniqueResult();
			//user= (User)session.createQuery("select from library.User where login = 'login'");
		} catch (Exception e) {
			return null;
		}finally {
			session.close();
		}
		
		return publisher;
	}

	@Override
	public List<Genre> getListGenre() {
		// TODO Auto-generated method stub
		return null;
	}

}
