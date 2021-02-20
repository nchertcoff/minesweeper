package com.minesweeper.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "game")
public class Game {

	private static final long serialVersionUID = -4098934944453342L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotBlank
	String username;

	Integer rows;

	Integer cols;

	Integer bombs;

	GameStatus status;

	@Transient
	Cell[][] cells;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
	private List<Cell> cellList = new ArrayList<>();

	public Game() {
	}

	public Game(String username, Integer rows, Integer cols, Integer bombs) {
		this.username = username;
		this.rows = rows;
		this.cols = cols;
		this.bombs = bombs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	@JsonIgnore
	public List<Cell> getCellList() {
		return cellList;
	}

	public void setCellList(List<Cell> cellList) {
		this.cellList = cellList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public Integer getBombs() {
		return bombs;
	}

	public void setBombs(Integer bombs) {
		this.bombs = bombs;
	}

	@JsonIgnore
	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

}
