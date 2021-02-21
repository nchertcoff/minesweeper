package com.minesweeper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minesweeper.domain.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	
	List<Game> findByUsername(String username);

	Game getById(Integer id);
	
}
