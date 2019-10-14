package ru.sds.ilibrary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.User;

@Component(value = "jdbcTemplateUserDao")
public class JdbcTemplateUserDao implements IUserDao {

	@Autowired
	@Qualifier(value= "jdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplateUserDao() {
	}
	

	public JdbcTemplateUserDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public List<User> getListUsers() {
		String sql = "select * from user ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		return jdbcTemplate.query(sql, params, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return new User(rs.getInt("id"), 
									rs.getString("name"),
										rs.getString("surname"), 
											rs.getString("login"), 
												rs.getString("password"));
			}
			
		});
		
	}

	@Override
	public User getUserByLogin(String userLogin) {
		
		User user = null;
		
		try {
			String sql = "select *from user where login= :login";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("login", userLogin);
			
			 user = jdbcTemplate.queryForObject(sql, params, new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return new User(rs.getInt("id"), 
										rs.getString("name"),
												rs.getString("surname"), 
													rs.getString("login"), 
														rs.getString("password"));
				}
				});
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
		
		if(user==null) {
			return null;
		}else 
			return user;
	}

	@Override
	public boolean addUser(User user) {
		String sql = "INSERT INTO library.User(name, surname, login, password) VALUES (:name, :surname, :login, :password)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", user.getName());
		params.addValue("surname", user.getSurname());
		params.addValue("login", user.getLogin());
		params.addValue("password", user.getPassword());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		 if(jdbcTemplate.update(sql, params, keyHolder)==1) {
			 return true;
		 }
		
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		String sql = "DELETE FROM library.User WHERE login = :login";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("login", user.getLogin());
		
		if(jdbcTemplate.update(sql, params)==1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteAllUser() {
		String sql = "DELETE FROM library.User";
		MapSqlParameterSource params = new MapSqlParameterSource();
		jdbcTemplate.update(sql, params);
			
		return true;
	}

}
