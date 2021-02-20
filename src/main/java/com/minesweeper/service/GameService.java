package com.minesweeper.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minesweeper.domain.Cell;
import com.minesweeper.domain.Game;
import com.minesweeper.domain.GameStatus;
import com.minesweeper.repository.GameRepository;

@Service
public class GameService {

	GameRepository gameRepository;
	
	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
	public void addGame(Game game) {
		distributeBombs(game);
		game.setStatus(GameStatus.IN_PROGRESS);
		gameRepository.save(game);
	}

	private void distributeBombs(Game game) {
		Integer rows = game.getRows();
		Integer cols = game.getCols();
		List<Cell> cellList = game.getCellList();
		Cell[][] cells = new Cell[rows][cols];
		game.setCells(cells);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = new Cell();
				cell.setRow(i);
				cell.setCol(j);
				cells[i][j]=cell;
				cell.setGame(game);
				cellList.add(cell);
			}
		}
		Random r = new Random();
		Integer bombs = game.getBombs();
		
		//Simple but not very efficient (a shuffle of positions is better)
		while(bombs>0) {
			Integer row = r.nextInt(rows);
			Integer col = r.nextInt(cols);
			if (!cells[row][col].isBomb()) {
				cells[row][col].setHasBomb(true);
				
				if (col+1<cols) cells[row][col+1].incrementBombsArround();
				if (col-1>=0) cells[row][col-1].incrementBombsArround();
				if (row+1<rows) cells[row+1][col].incrementBombsArround();
				if (row-1>=0) cells[row-1][col].incrementBombsArround();

				if (col+1<cols && row+1<rows) cells[row+1][col+1].incrementBombsArround();
				if (col-1>=0 && row-1>=0) cells[row-1][col-1].incrementBombsArround();
				if (col+1<cols && row-1>=0) cells[row-1][col+1].incrementBombsArround();
				if (col-1>=0 && row+1<rows) cells[row+1][col-1].incrementBombsArround();
				bombs--;
			}
		}	

	}

	public List<Game> getGames(String username) {
		return gameRepository.findByUsername(username);
	}
	
	
	
}
