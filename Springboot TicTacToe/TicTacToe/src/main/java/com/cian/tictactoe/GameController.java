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
			int[] move =findBestMove();
			if(move[0]!=-1) {
				currentSession.getBoard().move(move[0], move[1], GridText.O);
			}
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
	
	public int miniMaxMove(int depth, boolean isMax){
		
		 //Check if game is already over
		 if(currentSession.getBoard().checkWin(GridText.X)) {
			 return -10;
		 }else if(currentSession.getBoard().checkWin(GridText.O)) {
			 return 10;
		 }else if(currentSession.getBoard().isDraw()) {
			return 0;
		 }
		 
		 if(isMax) {
		 int best = -1000; 
		 
		  
	        // Traverse all cells 
	        for (int i = 0; i < 3; i++) 
	        { 
	            for (int j = 0; j < 3; j++) 
	            { 
	                // Check if cell is empty 
	                if (currentSession.getBoard().textAt(i,j)==" ") 
	                { 
	                    // Make the move 
	                	currentSession.getBoard().move(i, j, GridText.O);
	                 
	  
	                    // Call minimax recursively and choose 
	                    // the maximum value 
	                    best = Math.max(best, miniMaxMove(depth + 1, !isMax)); 
	  
	                    // Undo the move 
	                    currentSession.getBoard().clearSquare(i, j);
	                } 
	            } 
	        }
	        return best;
		 }else {
			 int best = 1000; 
			  
		        // Traverse all cells 
		        for (int i = 0; i < 3; i++) 
		        { 
		            for (int j = 0; j < 3; j++) 
		            { 
		                // Check if cell is empty 
		                if (currentSession.getBoard().textAt(i, j) == " ") 
		                { 
		                    // Make the move 
		                    currentSession.getBoard().move(i, j, GridText.X); 
		  
		                    // Call minimax recursively and choose 
		                    // the minimum value 
		                    best = Math.min(best, miniMaxMove( depth + 1, !isMax)); 
		  
		                    // Undo the move 
		                    currentSession.getBoard().clearSquare(i, j);
		                } 
		            } 
		        } 
		        return best; 
		 }
	}
	
	public int[] findBestMove() {
		int bestval =-1000;
		int[] move= {-1,-1};
		
		// Traverse all cells, evaluate minimax function  
	    // for all empty cells. And return the cell  
	    // with optimal value. 
	    for (int i = 0; i < 3; i++) 
	    { 
	        for (int j = 0; j < 3; j++) 
	        { 
	            // Check if cell is empty 
	            if (currentSession.getBoard().textAt(i, j) == " ") 
	            { 
	                // Make the move 
	                 currentSession.getBoard().move(i, j, GridText.O);
	  
	                // compute evaluation function for this 
	                // move. 
	                int moveVal = miniMaxMove( 0, false); 
	  
	                // Undo the move 
	                currentSession.getBoard().clearSquare(i, j);
	  
	                // If the value of the current move is 
	                // more than the best value, then update 
	                // best/ 
	                if (moveVal > bestval) 
	                { 
	                    move[0]=i;
	                    move[1]=j;
	                    bestval = moveVal; 
	                } 
	            } 
	        } 
	    } 
		
		return move;
		
	}

}