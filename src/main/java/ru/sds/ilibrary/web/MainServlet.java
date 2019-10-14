package ru.sds.ilibrary.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.sds.ilibrary.dao.impl.JdbcBookDao;
import ru.sds.ilibrary.dao.impl.JdbcUserDao;
import ru.sds.ilibrary.dao.interfaces.IBookDao;
import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.Book;
import ru.sds.ilibrary.model.entity.Genre;
import ru.sds.ilibrary.model.entity.User;
import ru.sds.ilibrary.service.impl.BookService;
import ru.sds.ilibrary.service.impl.SecurityService;
import ru.sds.ilibrary.service.impl.ServiceController;
import ru.sds.ilibrary.service.interfaces.IBookService;
import ru.sds.ilibrary.service.interfaces.ISecurityService;


public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServiceController serviceController;
	
       
   
    public MainServlet() {
    	IUserDao userDao = new JdbcUserDao();
    	IBookDao bookDao = new JdbcBookDao();
    	ISecurityService securityService = new SecurityService(userDao);
    	IBookService bookService = new BookService(bookDao);
    	serviceController = new ServiceController(securityService, bookService);
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html>");
		out.print("<body>");
		out.print("<h1> <a href=\"./main\"> Электронная библиотека</a></h1>");
		out.print("<h2>Информация о пользователе</h2>");
		User user = (User)request.getSession().getAttribute("user");
		out.print("<h3> Имя: "+user.getName()+"</h3>");
		out.print("<h3> Фамилия: "+user.getSurname()+"</h3>");
		
		out.print("<h2>Список жанров</h2>");
		List<Genre> genres = serviceController.getListGenre();
		for(Genre genre: genres) {
			out.print("<ul><li><a href=\"./main?genreId=" + genre.getId()+ "\">"+genre +"</a></li></ul>");
		}
		
		String genreId = request.getParameter("genreId");
		if (genreId != null) {
			List<Book> books = serviceController.getListBookByGanre(new Genre(Integer.valueOf(genreId),""));
			out.print("<h2>Список книг</h2>");
			for(Book book: books) {
				out.println("<p> <a href=\"./main?genreId="+Integer.valueOf(genreId)+"&bookName="+book.getBookName()+"\">"+book.getBookName()+"</a></p>");
			}
		}
		
		String bookName = request.getParameter("bookName");
		if(bookName !=null) {
			try {
				Book book = serviceController.getBookByName(bookName);
				out.print("<h2>Информация о книге</h2>");
				out.print("<h3>"+ "Имя книги: "+book.getBookName()+"</h3>");
				out.print("<h3>"+ "Автор книги: "+book.getAuthor().getName()+"</h3>");
				out.print("<h3>"+ "Жанр книги: "+book.getGenre().getGenreName()+"</h3>");
				out.print("<h3>"+ "Издатель книги: "+book.getPublisher().getPublisherName()+"</h3>");
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				out.print("<h2>Ошибочка</h2>");
			}
		}
		out.print("<h2> <a href=\"./\">Выход</h2>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
