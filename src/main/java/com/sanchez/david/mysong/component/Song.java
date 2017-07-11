package com.sanchez.david.mysong.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String name;
	@ManyToMany(mappedBy = "songList")
	@JsonBackReference
	private List<User> userList = new ArrayList<>();
	
	public Song(){
		
	}
	
	public Song(String title, String name){
		this.title = title;
		this.name = name;
	}
	
	public Song(String title, String name, List<User> userList){
		this.title = title;
		this.name = name;
		if (userList == null){
			userList = new ArrayList<>();
		} else {
			this.userList = userList;
		}
		
	}
	
	
	public List<User> getUserList(){
		return this.userList;
	}
	
	public void setUserList(List<User> userList){
		this.userList = userList;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Song other = (Song) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	
	

}
