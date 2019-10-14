package ru.sds.ilibrary.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.User;
import ru.sds.ilibrary.service.interfaces.ISecurityService;

@Component
public class SecurityService implements ISecurityService{
	
	@Autowired
	private IUserDao userDao;
	

	public SecurityService(IUserDao userDao) {
		super();
		this.userDao = userDao;
	}

	public SecurityService() {
		super();
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public boolean login(User user) {
		User tempUser = userDao.getUserByLogin(user.getLogin());
		System.out.println(user.getLogin());
		if(tempUser == null) {
			return false;
		} 
		return user.getPassword().equals(tempUser.getPassword());
		
	}

	public boolean logout(User user) {
		return true;
	}

	@Override
	public boolean addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public boolean deleteUser(User user) {
		return userDao.deleteUser(user);
	}

	@Override
	public boolean deleteAllUser() {
		
		return userDao.deleteAllUser();
	}

	@Override
	public User getUserByLogin(String login) {
		
		return userDao.getUserByLogin(login);
	}

}
