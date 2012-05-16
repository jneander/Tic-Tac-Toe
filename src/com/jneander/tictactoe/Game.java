package com.jneander.tictactoe;

public class Game {
  private int gameBoard[][];
  private Mark lastMove;
  private int playerMarks;

  public Game() {
    gameBoard = new int[3][3];

    clearGameBoard();
    playerMarks = 0;
  }

  private void clearGameBoard() {
    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        gameBoard[row][col] = -1;
  }

  public int[][] getGameBoard() {
    return gameBoard;
  }

  public void makePlayerMark( Mark mark ) {
    gameBoard[mark.row][mark.col] = 1;
    lastMove = mark;
    playerMarks++;
  }

  public void makeComputerMark() {
    Mark mark = new Mark(1,1);

    if ( playerMarks == 1 ) {
      if ( isCornerMark(lastMove) ) {
        mark = new Mark(1,1);
      }
    }

    gameBoard[mark.row][mark.col] = 0;
    lastMove = mark;
  }

  private boolean isCenterMark( Mark mark ) {
    return (mark.row == 1 && mark.col == 1);
  }

  private boolean isEdgeMark( Mark mark ) {
    boolean markIsOnEdge = false;

    if ( (mark.row + mark.col) % 2 == 1 )
      markIsOnEdge = true;

    return markIsOnEdge;
  }

  private boolean isCornerMark( Mark mark ) {
    return (!isCenterMark( mark ) && !isEdgeMark( mark ));
  }

  public Mark getLastMove() {
    return lastMove;
  }
}