package ru.sds.ilibrary.dao.impl;

import java.util.List;

import ru.sds.ilibrary.dao.interfaces.IGenreDao;
import ru.sds.ilibrary.model.entity.Genre;

public class StubGenreDao implements IGenreDao {

	public List<Genre> getListGenres() {
		// TODO Auto-generated method stub
		return null;
	}

	public Genre getGenreById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Genre getGenreByName(String nameGenre) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addGenre(Genre genre) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteGenre(Genre genre) {
		// TODO Auto-generated method stub
		return false;
	}

}
