package com.sanchez.david.mysong.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User {

	@Id
	private String alias;
	private String password;
	@ManyToMany
	@JoinTable(name = "user_songs",
	joinColumns = @JoinColumn(name="user_alias"),
	inverseJoinColumns = @JoinColumn(name="song_id"))
	@JsonManagedReference
	private List<Song> songList = new ArrayList<>();
	
	
	// Builder
	public User(){};
	
	public User(String alias, String password){
		this.alias = alias;
		this.password = password;
	}
	
	public User(String alias, String password, List<Song> songList){
		this.alias = alias;
		this.password = password;
		if (songList == null){
			songList = new ArrayList<>();
		} else {
			this.songList = songList;
		}
	}

	//Methods
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Song> getSongList(){
		return songList;
	}
	
	public void setSongList(List<Song> songList){
		this.songList = songList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
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
		User other = (User) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		return true;
	}
	
	
	
}
