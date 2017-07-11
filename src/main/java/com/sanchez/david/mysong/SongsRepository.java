package com.sanchez.david.mysong;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SongsRepository extends JpaRepository<Song, Long> {
	
	Song findByTitle(String title);
	Song findByTitleAndName(String title, String name);
	boolean existsByTitleAndName(String title, String name);

}
