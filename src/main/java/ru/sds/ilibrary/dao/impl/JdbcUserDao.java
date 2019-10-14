package ru.sds.ilibrary.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.User;

//@Component(value="jdbcUserDao")
public class JdbcUserDao implements IUserDao {
	
	private final String classDriverName = "com.mysql.jdbc.Driver";
	private final String jdbcUrl = "jdbc:mysql://localhost:3306/library";
	private final String SQL_SELECT_USERS = "SELECT * FROM library.User";
	private final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM library.User WHERE login = ?";
	private final String SQL_INSERT_INTO_USER = "INSERT INTO library.User(id, name, surname, login, password) VALUES (?,?,?,?,?)";
	private final String SQL_DELETE_USER = "DELETE FROM library.User WHERE id = ?";
	
	public JdbcUserDao() {
		try {
			Class.forName(classDriverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<User> getListUsers() {
		List<User> users = new ArrayList<User>();
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_USERS);
				) {
				while(resultSet.next()) {
				users.add(new User(resultSet.getInt("id"), 
									resultSet.getString("name"), 
										resultSet.getString("surname"), 
											resultSet.getString("login"), 
												resultSet.getString("password")));	
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return users;
	}

	@Override
	public User getUserByLogin(String userLogin) {
		User user = null;
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
					) {
			
			statement.setString(1, userLogin);
			
			try(ResultSet resultSet = statement.executeQuery();
					){
				
					while(resultSet.next()) {
					user = new User(resultSet.getInt("id"), 
										resultSet.getString("name"), 
											resultSet.getString("surname"), 
												resultSet.getString("login"), 
													resultSet.getString("password"));
					}
				}	
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		
		return user;
	}

	@Override
	public boolean addUser(User user) {
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_USER);
				) {
			statement.setInt(1, user.getId());
			statement.setString(2, user.getName());
			statement.setString(3, user.getSurname());
			statement.setString(4, user.getLogin());
			statement.setString(5, user.getPassword());
			if (statement.executeUpdate() == 0) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteUser(User user) {
		
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
				) {
			statement.setInt(1, user.getId());
			if(statement.executeUpdate()==0) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deleteAllUser() {
		try(Connection connection = DriverManager.getConnection(jdbcUrl, "root", "shheglo1996v");
				PreparedStatement statement = connection.prepareStatement("DELETE FROM library.User");
					) {
			statement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
	}

}
