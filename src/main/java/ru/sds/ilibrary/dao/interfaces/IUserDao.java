package ru.sds.ilibrary.dao.interfaces;

import java.util.List;

import ru.sds.ilibrary.model.entity.User;

public interface IUserDao {

	public List<User> getListUsers();
	
	public User getUserByLogin(String userLogin);
	
	public boolean addUser(User user);
	
	public boolean deleteUser(User user);
	
	public boolean deleteAllUser();
	
}
