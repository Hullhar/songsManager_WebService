package com.sanchez.david.mysong.controller;


import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanchez.david.mysong.component.Song;
import com.sanchez.david.mysong.component.User;
import com.sanchez.david.mysong.repository.SongsRepository;
import com.sanchez.david.mysong.repository.UserRepository;
import com.sanchez.david.mysong.resouces.HttpManager;
import com.sanchez.david.mysong.resouces.ParserXml;

@RestController
public class SongsController{
	
	@Autowired
	private SongsRepository repository;
	@Autowired
	private UserRepository uRepository;
	// Songs queries to obtain cover arts
	private final String GRACE_URL = "https://c82148243.web.cddbp.net/webapi/xml/1.0/";
	
	@PostConstruct
	public void init(){
		// Load Songs
		repository.save(new Song("Gravity","John Mayer",null));
		repository.save(new Song("Let it Matter","Johnnyswim",null));
		repository.save(new Song("Rock it","Pink",null));
		repository.save(new Song("Break","Metallyca",null));
		repository.save(new Song("Paradise","Coldplay",null));
		repository.save(new Song("Happy","Pharrel Williams",null));
		repository.save(new Song("Champion","Queen",null));
		repository.save(new Song("Moves like Jagger","Maroon5",null));
		repository.save(new Song("Yeah Baby","Rolling Stones",null));
		repository.save(new Song("Unsteady","Ambasador",null));
		
		
		// Load Users
		uRepository.save(new User("David","Da821984",null));
		uRepository.save(new User("Elena","1234",null));
		uRepository.save(new User("admin","0000",null));
		
		// Load songList
		User user = uRepository.findOne("admin");
		Song song1 = repository.findByTitle("Yeah Baby");
		Song song2 = repository.findByTitle("Unsteady");
		song1.getUserList().add(user);
		song2.getUserList().add(user);
		user.getSongList().add(song1);
		user.getSongList().add(song2);
		user.getSongList().add(song1);
		user.getSongList().add(song2);
		user.getSongList().add(song1);
		user.getSongList().add(song2);
		uRepository.save(user);
		repository.save(song1);
		repository.save(song2);
		
	}

	/**
	 * Return all songs in the repository
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value="/getAllSongs")
	public List<Song> getAllSongs(){
		List<Song> songsList = repository.findAll();
		return songsList;
	}
	/**
	 * Return a song with the title given
	 * @param title
	 * @return null if the song is not in the repository
	 */
	
	@CrossOrigin
	@RequestMapping(value="/getSong")
	public Song getSong(String title){
		Song song = null;
		if (title.isEmpty()==false){
			song = repository.findByTitle(title);
		}
		return song;
	}
	
	/**
	 * Add a song to the user list form the user given 
	 * @param song 
	 * @param alias User Name
	 * @return 
	 * 	1 = Everything is ok
	 * 	0 = User is not exist
	 * -1 = Something wrong occurs
	 */
	@CrossOrigin
	@RequestMapping(value="/addSong")
	public String addSong(Song song , @RequestParam("alias") String alias){
		String msg = "";
		User user = uRepository.findOne(alias);
		if (user != null){
			if (song != null && song.getName().isEmpty() == false && song.getTitle().isEmpty() == false){
				if (!repository.existsByTitleAndName(song.getTitle(),song.getName())){
					// Add cover art to the song
					String dataResponse = HttpManager.getData(GRACE_URL, song.getTitle(), song.getName());
					String img = ParserXml.parserImg(dataResponse);
					song.setImg(img);
					song.getUserList().add(user);
					repository.save(song);
					user.getSongList().add(song);
					uRepository.save(user);
				} else {
					if (!user.getSongList().contains(song)){
						Song aux = repository.findByTitleAndName(song.getTitle(), song.getName());
						aux.getUserList().add(user);
						user.getSongList().add(aux);
						uRepository.save(user);
					}
				}
				msg = "1";
			} else {
				msg = "-1";
			}
		} else {
			msg = "0";
		}
		return msg;
	}

	/**
	 * Remove a song in the user songs list from a given user
	 * @param song
	 * @param alias
	 * @return
	 * 	1 = Everything is ok
	 *  0 = Song is not in the user list
	 * -1 = Song is not a correct song
	 * -2 = Alias is not correct.
	 */
	@CrossOrigin
	@RequestMapping(value="/removeSong")
	public String removeSong(Song song, @RequestParam("alias") String alias){
		String msg = "";
		User user = uRepository.findOne(alias);
		if (user != null){
			if (song != null && song.getName().isEmpty() == false && song.getTitle().isEmpty() == false){
				if (user.getSongList().contains(song)){
					user.getSongList().remove(song);
					song.getUserList().remove(user);
					if (song.getUserList().size()==0){
						repository.delete(song);
					} else {
						repository.save(song);
					}
					uRepository.save(user);
					msg = "1";
				} else {
					msg = "0";
				}
			} else {
				msg = "-1";
			}
		} else {
			msg = "-2";
		}
		return msg;
	}
}
