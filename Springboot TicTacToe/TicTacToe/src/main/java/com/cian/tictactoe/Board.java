package com.cian.tictactoe;

public class Board {
	//The 3 possible text for a grid space
	enum GridText  {NONE,X,O};
	public GridText[][] gameBoard;
	
	//Representative of the game board , 3*3 =9
	public Board() {
		 gameBoard = new GridText[3][3];
		 clear();
	}
	
	
	/**
	 * Clears the board
	 */
	public void clear() {
		for(int i = 0;  i < 3;  i++ ) {
			for(int j = 0;  j < 3;  j++) {
				gameBoard[i][j] = GridText.NONE;
			}
		}
	}
	
	
	/**
	 * @Returns the text at a grid 
	 * @param row
	 * @param col
	 * @return
	 */
	public String textAt(int row, int col)
	{
		
		GridText text = gameBoard[row][col];
		//Returning the GridText item itself does not work, needs to be stringified
		if(text.equals(GridText.X)) {
			return "X";
		}
		else if(text.equals(GridText.O)) {
			return "O";
		}
		else if(text.equals(GridText.NONE)) {
			return " ";
		}
		//Should never get here Anyway
		return "?";
	}
	
	/**
	 * Takes a move
	 * @param row
	 * @param col
	 * @param GridText - What symbol should be placed here
	 */
	public void move(int row, int col, GridText text){
		if( gameBoard[row][col] != GridText.NONE) {
			System.out.println("Selected square already taken");
		}else {
			gameBoard[row][col] = text;
		}
	}
	
	
	/**
	 * Check if the specified player (X or O) won the game
	 * @param player - X or O
	 * @return boolean , true for won, false for did not win(Not necessarily a loss)
	 */
	public boolean checkWin(GridText player) {
		boolean won=false;
		
		//check downs
		for(int row=0;row<3;row++) {
			//assuming they have won is calculationally quicker
			won=true;
			for(int col=0;col<3 && won;col++) {
				if(gameBoard[row][col]!=player) {
					won=false;
				}
			}
			//Return true to stop unecessary calc
			if(won) {
				return won;
			}
			

		}
			
			//check across
			for(int col=0;col<3;col++) {
				won=true;
				for(int row=0;row<3 && won;row++) {
					if(gameBoard[row][col]!=player) {
						won=false;
					}
				}
				
				if(won) {
					return won;
				}

			}
			
			//Check Diags
			//This if statement is really ugly :(
			if((gameBoard[0][0] == player) && (gameBoard[1][1] == player) && (gameBoard[2][2] == player)) {
				return true;
			}
			if((gameBoard[2][0] == player) && (gameBoard[1][1] == player) && (gameBoard[0][2] == player)) {
				return true;
			}
			
			
		//If no win condition is met
		return false;
	}
	
	/**
	 * Check if the game is a draw
	 * @return true if the game is draw
	 */
	public boolean isDraw() {
		// Just check if all squares are taken
		//Probably a way to check if the game is a draw before all squares are full but oh well
		for(int row = 0 ;  row < 3;  row++) {
			for(int col = 0 ;  col < 3;  col++) {
				//If ANY square is empty, return false
				if(gameBoard[row][col].equals(GridText.NONE)) {
					return false;
				}
			}
		}
		return true;
	}
	

}
