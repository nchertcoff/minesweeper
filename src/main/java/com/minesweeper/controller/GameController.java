package com.minesweeper.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.minesweeper.domain.Game;
import com.minesweeper.exceptions.GameException;
import com.minesweeper.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

	GameService gameService;

	@Autowired
	public GameController(GameService employeeService) {
		this.gameService = employeeService;
	}
	
	/*
	 * Services
	 */
	@GetMapping
	public List<Game> getGames(
			@RequestParam(name = "username", required = true) String username) {
		return gameService.getGames(username);
	}

	@PostMapping
	public Game addGame(@RequestBody @Valid Game game) {
		gameService.addGame(game);
		return game;
	}

	/*
	 * Error handling
	 */
	@ExceptionHandler(GameException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public Map<String, String> handleCustomException(GameException ex) {
		Map<String, String> reponse = new HashMap<>();
		reponse.put("error", ex.getError());
		return reponse;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
