package com.jneander.tictactoe;

public class Game {
  int spaces[][];

  public Game() {
    spaces = new int[3][3];

    clearGameBoard();
  }

  private void clearGameBoard() {
    for ( int row = 0; row < spaces.length; row++ )
      for ( int col = 0; col < spaces[row].length; col++ )
        spaces[row][col] = -1;
  }

  public int[][] getGameBoard() {
    return spaces;
  }
}