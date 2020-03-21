package com.cian.tictactoe;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cian.tictactoe.Board.GridText;

@Controller
public class GameController {
	private Session currentSession;

	@RequestMapping("/")
	public String start(Model model) {
		currentSession = new Session();
		currentSession.newGame();
		model.addAttribute("board",currentSession.getBoard());
		return "index";
	}
	
	/**
	 * Resets the game
	 */
	@RequestMapping("/reset")
	public String reset(Model model) {
	    currentSession = new Session();
		currentSession.newGame();
		model.addAttribute("board",currentSession.getBoard());
		
		return "index";
	}
	
	
	/**
	 * Places a marker for the current player in the requested position.
	 * 
	 * @param session 
	 * @param row Number of row to place marker
	 * @param col Number of column to place marker
	 * @param model Spring framework Model
	 * @return Spring framework View name
	 */
	@RequestMapping( "/move")
	public String playerMove(
			@RequestParam(value = "row", required = true) Integer row, 
			@RequestParam(value = "col", required = true) Integer col,
			Model model) {
		
		model.addAttribute("board",currentSession.getBoard());
		
		// If not in a game just dont do anything
		if(!currentSession.getState().equals("in_progress")) {
			return "index";
		}
		
		Board board = currentSession.getBoard();
		try {
			board.move(row, col, currentSession.getTurn());
			checkBoard();
			
			//If the game is not over, AI will take a move
			miniMaxMove();
			checkBoard();
			
		}
		catch( Exception e )
		{
			System.out.println("Error has occured");
			e.printStackTrace();
		}
		
		return "index";
	}
	
	/**
	 * Check the board for wins/draws
	 */
	public void checkBoard() {
		Board board = currentSession.getBoard();
		// First, check for a draw
		if(board.isDraw()) {
			System.out.println("DRAW");
			currentSession.setState("over");
		}
		else if(board.checkWin(currentSession.getTurn())) {
			currentSession.setState("over");
			if(currentSession.getTurn().equals(GridText.O)) {
				System.out.println("O wins!");
			}
			else {
				System.out.println("X wins!");
			}
			
		}
		else
		{
			if(currentSession.getTurn() == GridText.X) {
				currentSession.setTurn(GridText.O);
			}
			else {
				currentSession.setTurn(GridText.X);
			}
		}
	}
	
	public void miniMaxMove(){
		//This is absoloutely not minimax, just here for testing
		 if(currentSession.getBoard().textAt(1, 1).equals(" ")){
			 currentSession.getBoard().move(1, 1, GridText.O);
		 }else if(currentSession.getBoard().textAt(0, 0).equals(" ")) {
			 currentSession.getBoard().move(0, 0, GridText.O);
		 }else if(currentSession.getBoard().textAt(2, 0).equals(" ")) {
			 currentSession.getBoard().move(2, 0, GridText.O);
		 }else if(currentSession.getBoard().textAt(0, 2).equals(" ")) {
			 currentSession.getBoard().move(0, 2, GridText.O);
		 }else if(currentSession.getBoard().textAt(2, 2).equals(" ")) {
			 currentSession.getBoard().move(2, 2, GridText.O);
		 }else if(currentSession.getBoard().textAt(0, 1).equals(" ")) {
			 currentSession.getBoard().move(0, 1, GridText.O);
		 }else if(currentSession.getBoard().textAt(1, 0).equals(" ")) {
			 currentSession.getBoard().move(1, 0, GridText.O);
		 }else if(currentSession.getBoard().textAt(1, 2).equals(" ")) {
			 currentSession.getBoard().move(1, 2, GridText.O);
		 }else if(currentSession.getBoard().textAt(2, 1).equals(" ")) {
			 currentSession.getBoard().move(2, 1, GridText.O);
		 }
		
	}

}