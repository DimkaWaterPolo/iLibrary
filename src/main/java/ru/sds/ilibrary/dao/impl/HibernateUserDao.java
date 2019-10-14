package ru.sds.ilibrary.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.User;

@Component
@Qualifier(value="hibernateUserDao")
public class HibernateUserDao implements IUserDao{

	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	
	public HibernateUserDao() {
	}

	
	public List<User> getListUsers() {
		List<User> users = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			users =  session.createCriteria(User.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return users;
	}

	
	public User getUserByLogin(String userLogin) {
		User user = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			System.out.println(userLogin);
			user = (User)session.createCriteria(User.class).add(Restrictions.eq("login", userLogin)).uniqueResult();
			//user= (User)session.createQuery("select from library.User where login = 'login'");
		} catch (Exception e) {
			return null;
		}finally {
			session.close();
		}
		return user;
	}

	
	public boolean addUser(User user) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(user);
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

	
	public boolean deleteUser(User user) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(user);
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

	
	public boolean deleteAllUser() {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("delete from User");
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
	
	public User getUserByID(int id) {
		User user = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			user = (User)session.get(User.class, id);
			//user= (User)session.createQuery("select from library.User where login = 'login'");
		} catch (Exception e) {
			return null;
		}finally {
			session.close();
		}
		return user;
	}

}
