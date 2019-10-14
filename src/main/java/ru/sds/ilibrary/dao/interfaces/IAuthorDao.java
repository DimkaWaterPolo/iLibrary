package ru.sds.ilibrary.dao.interfaces;
import java.util.List;

import ru.sds.ilibrary.model.entity.Author;

public interface IAuthorDao {

	public List<Author> getListAuthors();
	
	public Author getAuthorById(int id);
	
	public Author getAuthorByName(String authorName);
	
	public boolean addAuthor(Author author);
	
	public boolean deleteAuthor(Author author);
}
