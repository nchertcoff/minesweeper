package com.minesweeper.domain;

import javax.validation.constraints.NotBlank;

public class Game {

	Integer id;

	String username;
	
	@NotBlank
	Integer rows;

	@NotBlank
	Integer cols;
	
	@NotBlank
	Integer bombs;
	
	Cell[][] cells = null;
		
	public Game() {
	}

	public Game(Integer id, String username, Integer rows, Integer cols, Integer bombs) {
		this.id = id;
		this.username = username;
		this.rows = rows;
		this.cols = cols;
		this.bombs = bombs;
	}
	
}
