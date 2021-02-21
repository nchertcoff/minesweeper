package com.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minesweeper.domain.Cell;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
	
	Cell getByGameIdAndRowAndCol(Integer id, Integer row, Integer col);
	
}
