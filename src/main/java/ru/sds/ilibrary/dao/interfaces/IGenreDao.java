package ru.sds.ilibrary.dao.interfaces;
import java.util.List;

import ru.sds.ilibrary.model.entity.Genre;

public interface IGenreDao {
	
	public List<Genre> getListGenres();
	
	public Genre getGenreById(int id);
	
	public Genre getGenreByName(String nameGenre);
	
	public boolean addGenre(Genre genre);
	
	public boolean deleteGenre(Genre genre);

}
