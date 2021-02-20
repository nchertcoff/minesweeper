package com.minesweeper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minesweeper.domain.Game;
import com.minesweeper.repository.GameRepository;

@Service
public class GameService {

	GameRepository gameRepository;
	
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
	public void addGame(Game game) {
		gameRepository.save(game);
	}

	public List<Game> getGames(String username) {
		return gameRepository.findByUsername(username);
	}
	
}
