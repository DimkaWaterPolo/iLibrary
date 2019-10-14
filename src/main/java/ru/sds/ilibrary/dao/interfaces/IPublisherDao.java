package ru.sds.ilibrary.dao.interfaces;

import java.util.List;

import ru.sds.ilibrary.model.entity.Publisher;;

public interface IPublisherDao {

	public List<Publisher> getListPublishers();
	
	public Publisher getPublisherById(int id);
	
	public Publisher getPublisherByName(String namePublisher);
	
	public boolean addPublisher(Publisher publisher);
	
	public boolean deletePublisher(Publisher publisher);
}
