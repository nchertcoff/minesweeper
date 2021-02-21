package com.minesweeper.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cell")
public class Cell {

	private static final long serialVersionUID = -30303099955999L;
	
	public Cell() {
	}
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	//For testing purposes
	public Cell(Integer row, Integer col, boolean isBomb, Integer bombsArround) {
		this.row = row;
		this.col = col;
		this.isBomb = isBomb;
		this.bombsArround = bombsArround;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="game_id", nullable=false)
	Game game;
	
    Integer row;
    Integer col;
    
	boolean isBomb;
	int bombsArround;
	boolean flagged;
	boolean visited;
	boolean questionMarked;
	
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
	public void incrementBombsArround() {
		this.bombsArround++;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getCol() {
		return col;
	}
	public void setCol(Integer col) {
		this.col = col;
	}
	public boolean isQuestionMarked() {
		return questionMarked;
	}
	public void setQuestionMarked(boolean questionMarked) {
		this.questionMarked = questionMarked;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}

	@Override
	public String toString() {
		if (this.isBomb) return "B ";
		if (bombsArround>0) return ""+bombsArround+" ";
		return "  ";
	}

}
