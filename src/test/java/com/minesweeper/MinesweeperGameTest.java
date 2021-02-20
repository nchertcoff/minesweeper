package com.minesweeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.minesweeper.domain.Game;

public class MinesweeperGameTest {
    Game game;

    @BeforeEach
    void setUp() throws Exception {
        game = new Game(1, "nchert", 10, 10, 10);
    }

    @Test
    void testGame() {
    }
    
}
