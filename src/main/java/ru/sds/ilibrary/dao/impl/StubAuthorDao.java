package ru.sds.ilibrary.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.sds.ilibrary.dao.interfaces.IAuthorDao;
import ru.sds.ilibrary.model.entity.Author;

@Component
public class StubAuthorDao implements IAuthorDao {
	
	List<Author> authors = new ArrayList<Author>();
	
	

	public StubAuthorDao() {
		authors.add(new Author(1, "authorName1"));
		authors.add(new Author(2, "authorName2"));
		authors.add(new Author(3, "authorName3"));
		authors.add(new Author(4, "authorName4"));
	}

	public List<Author> getListAuthors() {	
		return authors;
	}

	public Author getAuthorById(int id) {
		Author author = null;
		
		for(Author auth: authors) {
			if(auth.getId() == id) {
				author=auth;
			}
		}
		return author;
	}

	public Author getAuthorByName(String authorName) {
		Author author = null;
		
		for(Author auth: authors) {
			if(auth.getName().equals(authorName)) {
				author=auth;
			}
		}
		return author;
	}

	public boolean addAuthor(Author author) {
		boolean a = false;
		
		for(int i=0; i<authors.size();i++) {
			if(!(authors.get(i).getId() == author.getId())) {
				a = true;	
			}else
				{a=false;
				break;
				}
		}
		
		if(a==true) {
			authors.add(author);
		}
		
		return a;
	}

	public boolean deleteAuthor(Author author) {
		if(authors.remove(author)) {
			return true;
		}
		return false;
	}

}
