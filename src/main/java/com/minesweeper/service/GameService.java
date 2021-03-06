package com.minesweeper.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minesweeper.domain.Cell;
import com.minesweeper.domain.Game;
import com.minesweeper.domain.GameStatus;
import com.minesweeper.repository.CellRepository;
import com.minesweeper.repository.GameRepository;

@Service
public class GameService {

	GameRepository gameRepository;
	CellRepository cellRepository;
	
	@Autowired
	public GameService(GameRepository gameRepository, CellRepository cellRepository) {
		this.gameRepository = gameRepository;
		this.cellRepository = cellRepository;
	}
	
	public void createGame(Game game) {
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
				Cell cell = new Cell(i, j);
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
	
	public Game getGame(Integer id) {
		Game game = gameRepository.getById(id);
		return game;
	}
	
	public void visitCell(Game game, Integer row, Integer col) {
		if (!GameStatus.IN_PROGRESS.equals(game.getStatus())) {
			return;
		}
		
		Cell[][] cells = game.getCells();
		Integer rows = game.getRows();
		Integer cols = game.getCols();
		
		if (cells[row][col].isVisited()) {
			//Already visited
			return;
		}
		cells[row][col].setVisited(true);
		cells[row][col].setQuestionMarked(false);
		cells[row][col].setFlagged(false);
		if (!cells[row][col].isBomb()) {
			//If the cell doesn't have bombs around we visit all of them (and so on)
			if (cells[row][col].getBombsArround()==0) {
				if (col+1<cols) visitCell(game, row, col+1);
				if (col-1>=0) visitCell(game, row, col-1);
				if (row+1<rows) visitCell(game, row+1, col);
				if (row-1>=0) visitCell(game, row-1, col);
				if (col+1<cols && row+1<rows) visitCell(game, row+1,col+1);
				if (col-1>=0 && row-1>=0) visitCell(game, row-1,col-1);
				if (col+1<cols && row-1>=0) visitCell(game, row-1,col+1);
				if (col-1>=0 && row+1<rows) visitCell(game, row+1,col-1);
			}
		} else {
			//We visit a bomb :(
			game.setStatus(GameStatus.LOSE);
		}
		checkIfCompleted(game);
		gameRepository.save(game);
	}

	protected void checkIfCompleted(Game game) {
		if (game==null) {
			return;
		}
		
		boolean cellIncorrectlyFlagged = false;
		Cell[][] cells = game.getCells();
		Integer rows = game.getRows();
		Integer cols = game.getCols();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!cells[i][j].isVisited() && !cells[i][j].isFlagged()) {
					return;
				}
				if (cells[i][j].isFlagged() && !cells[i][j].isBomb()) { 
					cellIncorrectlyFlagged = true;
					if (cellIncorrectlyFlagged) { 
						//TODO: Return a warning for using more flags than bombs 
						return;
					}
				}
			}
		}
		game.setStatus(GameStatus.WIN);
	}

	public Cell getCell(Integer gameId, Integer row, Integer col) {
		Cell cell = cellRepository.getByGameIdAndRowAndCol(gameId, row, col);
		return cell;
	}
	
	/**
	 * Mark the cell as flagged or as question mark
	 * @param gameId
	 * @param cell
	 */
	public void modifyCell(Integer gameId, Cell cell) {
		Game game = getGame(gameId);
		Cell savedCell = getCell(gameId, cell.getRow(), cell.getCol());
		
		//Nothing to mark if the cell is already visited
		if (savedCell.isVisited()) {
			return;
		}
		
		savedCell.setFlagged(cell.isFlagged());
		savedCell.setQuestionMarked(cell.isQuestionMarked());
		checkIfCompleted(game);
		cellRepository.save(savedCell);
		
	}
	
}
