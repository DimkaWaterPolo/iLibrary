package ru.sds.ilibrary.model.entity;

public class Genre {

	private int id;
	private String genreName;

	
	
	public Genre() {
	}



	public Genre(int id, String genreName) {
		super();
		this.id = id;
		this.genreName = genreName;
	}

	
	
	public Genre(String genreName) {
		super();
		this.genreName = genreName;
	}



	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genre) {
		this.genreName = genre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genreName == null) ? 0 : genreName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (genreName == null) {
			if (other.genreName != null)
				return false;
		} else if (!genreName.equals(other.genreName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return genreName;
	}
	
	
}
