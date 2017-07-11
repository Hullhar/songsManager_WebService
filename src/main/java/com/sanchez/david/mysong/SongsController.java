package com.sanchez.david.mysong;


import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongsController{
	
	@Autowired
	private SongsRepository repository;
	
	@PostConstruct
	public void init(){
		repository.save(new Song("Gravity","John Mayer"));
		repository.save(new Song("Let it Matter","Johnnyswim"));
		repository.save(new Song("Rock it","Pink"));
		repository.save(new Song("Break","Metallyca"));
		repository.save(new Song("Paradise","Coldplay"));
		repository.save(new Song("Happy","Pharrel Williams"));
		repository.save(new Song("Champion","Queen"));
		repository.save(new Song("Moves like Jagger","Maroon5"));
		repository.save(new Song("Yeah Baby","Rolling Stones"));
		repository.save(new Song("Unsteady","Ambasador"));
		
	}

	@CrossOrigin
	@RequestMapping(value="/getAllSongs")
	public List<Song> getAllSongs(){
		List<Song> songsList = repository.findAll();
		return songsList;
	}
	
	@CrossOrigin
	@RequestMapping(value="/getSong")
	public Song getSong(String title){
		Song song = null;
		if (title.isEmpty()==false){
			song = repository.findByTitle(title);
		}
		return song;
	}
	
	@CrossOrigin
	@RequestMapping(value="/addSong")
	public String addSong(Song song){
		String msg = "";
		if (song != null && song.getName().isEmpty() == false && song.getTitle().isEmpty() == false){
			if (!repository.existsByTitleAndName(song.getTitle(),song.getName())){
				repository.save(song);
			} else {
				Song aux = repository.findByTitleAndName(song.getTitle(), song.getName());
				aux.setTitle(song.getTitle());
				aux.setName(song.getName());
				repository.save(aux);
			}
			msg = "1";
		} else {
			msg = "-1";
		}
		return msg;
	}

	@CrossOrigin
	@RequestMapping(value="/removeSong")
	public String removeSong(Song song){
		String msg = "";
		if (song != null && song.getName().isEmpty() == false && song.getTitle().isEmpty() == false){
			if (repository.exists(song.getId())){
				repository.delete(song);
				msg = "1";
			} else {
				msg = "0";
			}
		} else {
			msg = "-1";
		}
		return msg;
	}
}
