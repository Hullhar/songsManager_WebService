package com.sanchez.david.mysong.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanchez.david.mysong.component.Song;
import com.sanchez.david.mysong.component.User;
import com.sanchez.david.mysong.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository uRepository;
	
	@RequestMapping(value="/getAllUsers")
	@CrossOrigin
	public List<User> getAllUsers(){
		return uRepository.findAll();
	}
	
	/**
	 * Return an User with the given alias.
	 * @param alias User Name
	 * @return if alias exists, return a valid User Object.
	 * 			if not, return null. 
	 */
	@RequestMapping(value="/getUser")
	@CrossOrigin
	public User getUser(String alias){
		User user = null;
		if (uRepository.exists(alias)){
			user = uRepository.findOne(alias);
		}
		return user;
	}
	
	/**
	 * Return a user song list from a valid user.
	 * @param alias User Name
	 * @return a list with the user songs
	 */
	@RequestMapping(value="/getUserSongs")
	@CrossOrigin
	public List<Song> getUserSongs(String alias){
		List<Song> songList = null;
		if (uRepository.exists(alias)){
			songList = uRepository.findOne(alias).getSongList();
		} else {
			songList = new ArrayList<>();
		}
		return songList;
	}
	
	/**
	 * Create a new user
	 * Return:
	 * 	1 = Everything is ok
	 *  0 = User already exists
	 * -1 = Error occurs. Could not be created
	 */
	@RequestMapping(value = "/newUser")
	@CrossOrigin
	public String newUser(User user){
		String result = "";
		if (user != null){
			if (uRepository.exists(user.getAlias())){
				result = "0";
			} else {
				uRepository.save(user);
				result = "1";
			}
		} else {
			result = "-1";
		}
		return result;
	}
	
	/**
	 * Remove an existing user
	 * @return
	 * 	1 = Everything is ok
	 *  0 = User is not exists 
	 */
	@RequestMapping( value = "/removeUser")
	@CrossOrigin
	public String removeUser(String al){
		String result = "";
		if ( uRepository.exists(al)){
			uRepository.delete(al);
			result = "1";
		} else {
			result = "0";
		}
		return result; 
	}
	
}
