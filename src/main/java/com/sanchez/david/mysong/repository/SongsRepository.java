package com.sanchez.david.mysong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanchez.david.mysong.component.Song;

public interface SongsRepository extends JpaRepository<Song, Long> {
	
	Song findByTitle(String title);
	Song findByTitleAndName(String title, String name);
	boolean existsByTitleAndName(String title, String name);

}
