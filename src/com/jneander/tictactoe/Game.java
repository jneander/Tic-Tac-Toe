package com.jneander.tictactoe;

import com.jneander.tictactoe.Mark.MarkType;

public class Game {
  private MarkType gameBoard[][];
  private Mark lastMark;
  private int playerMarks;

  public Game() {
    gameBoard = new MarkType[3][3];

    clearGameBoard();
    playerMarks = 0;
  }

  private void clearGameBoard() {
    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        gameBoard[row][col] = MarkType.BLANK;
  }

  public MarkType[][] getGameBoard() {
    return gameBoard;
  }

  public void makePlayerMark( Mark mark ) {
    gameBoard[mark.row][mark.col] = MarkType.PLAYER;
    lastMark = mark;
    playerMarks++;
  }

  public void makeComputerMark() {
    Mark mark = new Mark( 1, 1, MarkType.COMPUTER );

    if ( playerMarks == 1 ) {
      if ( isCornerMark( lastMark ) ) {
        mark = new Mark( 1, 1, MarkType.COMPUTER );
      } else if ( isEdgeMark( lastMark ) ) {
        mark = new Mark( 1, 1, MarkType.COMPUTER );
      } else {
        mark = new Mark( 0, 0, MarkType.COMPUTER );
      }
    }

    gameBoard[mark.row][mark.col] = mark.markType;
    lastMark = mark;
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

  public Mark getLastMark() {
    return lastMark;
  }
}