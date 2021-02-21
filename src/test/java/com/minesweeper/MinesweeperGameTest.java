package com.minesweeper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.minesweeper.domain.Cell;
import com.minesweeper.domain.Game;
import com.minesweeper.domain.GameStatus;
import com.minesweeper.repository.CellRepository;
import com.minesweeper.repository.GameRepository;
import com.minesweeper.service.GameService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinesweeperGameTest {
	
	@MockBean
    GameRepository gameRepository;
	
	@MockBean
    CellRepository cellRepository;
	
	GameService gameService;
	
	Game preparedGame = new Game("nchert", 4, 4, 3);

	@BeforeEach
    void setUp() throws Exception {
    	gameService = new GameService(gameRepository, cellRepository);
    	
    	/* preparedGame
    	 *  
    	 * 1 2 B 1 
    	 * B 2 1 1 
    	 * 1 1 1 1
    	 *     1 B
    	 */
    	Cell[][] cells=new Cell[4][4];
    	cells[0][0]=new Cell(0, 0, false, 1);
    	cells[0][1]=new Cell(0, 1, false, 2);
    	cells[0][2]=new Cell(0, 2, true, 0);
    	cells[0][3]=new Cell(0, 3, false, 1);
    	cells[1][0]=new Cell(1, 0, true, 0);
    	cells[1][1]=new Cell(1, 1, false, 2);
    	cells[1][2]=new Cell(1, 2, false, 1);
    	cells[1][3]=new Cell(1, 3, false, 1);
    	cells[2][0]=new Cell(2, 0, false, 1);
    	cells[2][1]=new Cell(2, 1, false, 1);
    	cells[2][2]=new Cell(2, 2, false, 1);
    	cells[2][3]=new Cell(2, 3, false, 1);
    	cells[3][0]=new Cell(3, 0, false, 0);
    	cells[3][1]=new Cell(3, 1, false, 0);
    	cells[3][2]=new Cell(3, 2, false, 1);
    	cells[3][3]=new Cell(3, 3, true, 0);
    	preparedGame.setCells(cells);
    	preparedGame.setStatus(GameStatus.IN_PROGRESS);
    	
    	Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(null);
    }

    @Test
    void testCreate() {
    	Game game = new Game("nchert", 10, 10, 10);
    	gameService.createGame(game);
    	assertEquals(game.getStatus(), GameStatus.IN_PROGRESS);
    }

    @Test
    void testVisitCell() {
    	Cell firstCell = preparedGame.getCells()[0][0]; 
    	assertFalse(firstCell.isVisited());
    	gameService.visitCell(preparedGame, 0, 0);
    	assertTrue(firstCell.isVisited());
    }

    @Test
    void testVisitEmptyCell() {
    	assertEquals(visitedCells(preparedGame), 0);
    	gameService.visitCell(preparedGame, 3, 0);
    	//6 cells must be visited (on the left bottom corner)
    	assertEquals(visitedCells(preparedGame), 6);
    }

    private int visitedCells(Game game) {
    	int count = 0;
		for (int i = 0; i < game.getRows(); i++) {
			for (int j = 0; j < game.getCols(); j++) {
				if (game.getCells()[i][j].isVisited()) {
					count ++;
				}
			}
		}
		return count;
	}

	@Test
    void testVisitBomb() {
		gameService.visitCell(preparedGame, 0, 2);
		assertEquals(preparedGame.getStatus(), GameStatus.LOSE);
    }

    @Test
    void testFlagAndQuestionMark() {
    	
    	Integer gameId = preparedGame.getId();
    	Cell firstCell = preparedGame.getCells()[0][0]; 
    	Mockito.when(cellRepository.getByGameIdAndColAndRow(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(firstCell);
    	assertFalse(firstCell.isFlagged());
    	
    	Cell modifyCell = new Cell(0,0, true, false);
    	gameService.modifyCell(gameId, modifyCell);
    	assertTrue(firstCell.isFlagged());    	
    	
    	modifyCell.setFlagged(false);
    	gameService.modifyCell(gameId, modifyCell);
    	assertFalse(firstCell.isFlagged());    	

    	assertFalse(firstCell.isQuestionMarked());
    	modifyCell.setQuestionMarked(true);
    	gameService.modifyCell(gameId, modifyCell);
    	assertTrue(firstCell.isQuestionMarked());
    	
    	modifyCell.setQuestionMarked(false);
    	gameService.modifyCell(gameId, modifyCell);
    	assertFalse(firstCell.isQuestionMarked());    	
    }

    @Test
    void testWon() {
    	gameService.visitCell(preparedGame, 0, 0);
    	gameService.visitCell(preparedGame, 0, 1);
    	
    	preparedGame.getCells()[0][2].setFlagged(true);
    	gameService.visitCell(preparedGame, 0, 3);

    	preparedGame.getCells()[1][0].setFlagged(true);
    	gameService.visitCell(preparedGame, 1, 1);
    	gameService.visitCell(preparedGame, 1, 2);
    	gameService.visitCell(preparedGame, 1, 3);

    	gameService.visitCell(preparedGame, 2, 3);
    	preparedGame.getCells()[3][3].setFlagged(true);
    	gameService.visitCell(preparedGame, 3, 0);
    	
		assertEquals(preparedGame.getStatus(), GameStatus.WIN);
    }

    /*
     * Private helper methods
     */
	private void printCells(Game game) {
		Cell[][] cells = game.getCells();
		Integer rows = game.getRows();
		Integer cols = game.getCols();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(cells[i][j]);
			}
			System.out.println();
		}
	}
	    
}

