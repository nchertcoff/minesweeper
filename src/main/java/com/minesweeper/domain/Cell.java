package com.minesweeper.domain;

public class Cell {

	boolean isBomb;
	int bombsArround;
	boolean flagged;
	boolean visited;
	boolean questionMarked;
	
	public Cell() {
	}
	
	public Cell(boolean isBomb, int bombsArround, boolean flagged, boolean visited) {
		this.isBomb = isBomb;
		this.bombsArround = bombsArround;
		this.flagged = flagged;
		this.visited = visited;
	}
	
	public boolean isBomb() {
		return isBomb;
	}
	public void setHasBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}
	public int getBombsArround() {
		return bombsArround;
	}
	public void setBombsArround(int bombsArround) {
		this.bombsArround = bombsArround;
	}
	public boolean isFlagged() {
		return flagged;
	}
	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	
}
