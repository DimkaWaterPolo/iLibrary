package ru.sds.ilibrary.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IUserDao;
import ru.sds.ilibrary.model.entity.User;


public class StubUserDao implements IUserDao {
	
	List<User> users = new ArrayList<User>();
	
	public StubUserDao() {
		users.add(new User(1, "Dima", "Scheglov", "shheglo1996v", "12345"));
		users.add(new User(2, "Dima", "Scheglov", "shheglo1996v", "12345"));
		users.add(new User(3, "Dima", "Scheglov", "shheglo1996v", "12345"));
		users.add(new User(4, "Dima", "Scheglov", "shheglo1996v", "12345"));
	}

	public List<User> getListUsers() {	
		return users;
	}

	public User getUserByLogin(String login) {
		User user = null;
		for(User u: users) {
			if(u.getLogin().equals(login)) {
				user=u;
			}
		}
		return user;
	}

	public boolean addUser(User user) {	
		for(User us: users) {
			if(us.getId() == user.getId()) {
				return false;
			}
		}
		return users.add(user);
	}

	public boolean deleteUser(User user) {
		return users.remove(user);
	}

	@Override
	public boolean deleteAllUser() {
		// TODO Auto-generated method stub
		return false;
	}

}
