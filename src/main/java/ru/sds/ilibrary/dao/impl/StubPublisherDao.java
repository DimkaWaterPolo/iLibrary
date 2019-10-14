package ru.sds.ilibrary.dao.impl;

import java.util.ArrayList;
import java.util.List;

import ru.sds.ilibrary.dao.interfaces.IPublisherDao;
import ru.sds.ilibrary.model.entity.Publisher;
import ru.sds.ilibrary.service.impl.BookService;

public class StubPublisherDao implements IPublisherDao{

	List<Publisher> publishers = new ArrayList<Publisher>();
	
	
	
	public StubPublisherDao() {
		publishers.add(new Publisher(1, "publisherName1"));
		publishers.add(new Publisher(2, "publisherName2"));
		publishers.add(new Publisher(3, "publisherName3"));
		publishers.add(new Publisher(4, "publisherName4"));
	}

	public List<Publisher> getListPublishers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Publisher getPublisherById(int id) {
		
		return null;
	}

	public Publisher getPublisherByName(String namePublisher) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addPublisher(Publisher publisher) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletePublisher(Publisher publisher) {
		// TODO Auto-generated method stub
		return false;
	}

}
