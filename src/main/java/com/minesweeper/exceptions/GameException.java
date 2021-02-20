package com.minesweeper.exceptions;

public class GameException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    String error;

    public GameException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
    
}
