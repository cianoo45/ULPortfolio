package com.cian.tictactoe;

import com.cian.tictactoe.Board.GridText;

public class Session {
	
	private Board gameBoard;
	//This should really be done another way , State Design pattern probably
	private String gameState="waiting";
	private GridText turn;
	
	public Session() {
		gameBoard = new Board();
	}
	
	/**
	 * Reset the game
	 */
	public void reset() {
		gameBoard.clear();
	}
	
	public void setState(String s) {
		gameState =s;
	}
	
	public String textAt(int row) {
		return gameBoard.textAt(row, 0);
	}
	
	
	
	public String getState() {
		return gameState;
	}
	
	public void newGame() {
		setState("in_progress");
		setTurn(GridText.X);
	}
	
	public void setTurn(GridText t) {
		turn=t;
	}
	
	public GridText getTurn() {
		return turn;
	}
	
	public Board getBoard() {
		return gameBoard;
	}

}
