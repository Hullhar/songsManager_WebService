package com.sanchez.david.mysong;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String name;
	
	public Song(){
		
	}
	
	public Song(String title, String name){
		this.title = title;
		this.name = name;
		
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

}
