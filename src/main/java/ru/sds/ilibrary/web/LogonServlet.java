package ru.sds.ilibrary.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.sds.ilibrary.dao.impl.JdbcBookDao;
import ru.sds.ilibrary.dao.impl.JdbcUserDao;
import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.User;
import ru.sds.ilibrary.service.impl.BookService;
import ru.sds.ilibrary.service.impl.SecurityService;
import ru.sds.ilibrary.service.impl.ServiceController;
import ru.sds.ilibrary.service.interfaces.IBookService;
import ru.sds.ilibrary.service.interfaces.ISecurityService;


public class LogonServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	ServiceController serviceController;

   
    public LogonServlet() {
    	IUserDao userDao = new JdbcUserDao();
    	IBookDao bookDao = new JdbcBookDao();
    	ISecurityService securityService = new SecurityService(userDao);
    	IBookService bookService = new BookService(bookDao);
    	serviceController = new ServiceController(securityService, bookService);
    }
    
    

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String password = request.getParameter("password");
    	String login = request.getParameter("login");
    	
 		if(serviceController.login(new User(login, password))) {
 			User user = serviceController.getUserByLogin(login);
 			HttpSession session = request.getSession(true);
 			session.setAttribute("user", user);
 			response.sendRedirect("./main");
    	}else {
    		response.sendRedirect("./error");
    	}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
