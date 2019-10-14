package ru.sds.ilibrary.main;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.sds.ilibrary.dao.impl.HibernateBookDao;
import ru.sds.ilibrary.dao.impl.HibernateUserDao;
import ru.sds.ilibrary.dao.impl.JdbcBookDao;
import ru.sds.ilibrary.dao.impl.JdbcTemplateBookDao;
import ru.sds.ilibrary.dao.impl.JdbcTemplateUserDao;
import ru.sds.ilibrary.dao.impl.JdbcUserDao;
import ru.sds.ilibrary.dao.impl.StubAuthorDao;
import ru.sds.ilibrary.dao.impl.StubBookDao;
import ru.sds.ilibrary.dao.impl.StubUserDao;
import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.Author;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.Publisher;
import ru.sds.ilibrary.model.entity.User;
import ru.sds.ilibrary.service.impl.BookService;
import ru.sds.ilibrary.service.impl.SecurityService;
import ru.sds.ilibrary.service.impl.ServiceController;
import ru.sds.ilibrary.service.interfaces.IBookService;
import ru.sds.ilibrary.service.interfaces.ISecurityService;



public class Start {

	public static void main(String[] args) {
	
		Logger logger = Logger.getLogger(Start.class);
		
		logger.debug("Чтение xml файла");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-annotation.xml");
		logger.debug("Файл прочитан");
		
		logger.debug("Получение бинов");
		//ServiceController serviceController = (ServiceController) context.getBean("serviceController");
		logger.debug("Бины получены");
		//System.out.println(serviceController.login(new User(5, "Dima", "Scheglov", "shheglo1996v", "12345")));
		
		
		
			
			HibernateUserDao hibernateUserDao = (HibernateUserDao) context.getBean("hibernateUserDao");
		
		System.out.println(hibernateUserDao.getUserByLogin("shheglo1996v"));
		System.out.println(hibernateUserDao.deleteAllUser());
		
		HibernateBookDao hibernateBookDao = (HibernateBookDao) context.getBean("hibernateBookDao");
		
			System.out.println(hibernateBookDao.getListBookByGanre(new Genre(333, "yshas44")));
		
		
		//System.out.println(hibernateBookDao.getListBookByGanre(new Genre(331,"genreName1")));
		//System.out.println(hibernateBookDao.addBook(new Book("book3", new Author("dima"), new Genre("yshas"), new Publisher("detstvo"))));
		//System.out.println(hibernateBookDao.addBook(new Book("book44", new Author("dima44"), new Genre("yshas44"), new Publisher("detstvo44"))));
	}
}
