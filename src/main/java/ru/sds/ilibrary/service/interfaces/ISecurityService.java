package ru.sds.ilibrary.service.interfaces;

import ru.sds.ilibrary.model.entity.User;

public interface ISecurityService {

	public boolean login(User user);
	
	public boolean logout(User user);
	
	public boolean addUser(User user);
	
	public boolean deleteUser(User user);
	
	public boolean deleteAllUser();
	
	public User getUserByLogin(String login);
}
