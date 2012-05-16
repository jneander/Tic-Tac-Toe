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
    boolean madeMark = false;

    if ( playerMarks == 1 ) {
      if ( isCornerMark( lastMark ) ) {
        mark = new Mark( 1, 1, MarkType.COMPUTER );
      } else if ( isEdgeMark( lastMark ) ) {
        mark = new Mark( 1, 1, MarkType.COMPUTER );
      } else {
        mark = new Mark( 0, 0, MarkType.COMPUTER );
      }
    } else {
      for ( int row = 0; (row < gameBoard.length) && !madeMark; row++ ) {
        if ( setNeedsBlock( gameBoard[row] ) )
          mark = new Mark( row, findBlockPosition( gameBoard[row] ), MarkType.COMPUTER );
      }
      for ( int col = 0; (col < gameBoard[0].length) && !madeMark; col++ ) {
        MarkType colMarks[] = getGameBoardColumn(col);
        if ( setNeedsBlock( colMarks ) )
          mark = new Mark( findBlockPosition( colMarks ), col, MarkType.COMPUTER );
      }

    }

    gameBoard[mark.row][mark.col] = mark.markType;
    lastMark = mark;
  }

  private MarkType[] getGameBoardColumn( int col ) {
    MarkType marks[] = new MarkType[gameBoard.length];
    
    for (int row = 0; row < gameBoard.length; row++)
      marks[row] = gameBoard[row][col];
    
    return marks;
  }

  private boolean setNeedsBlock( MarkType marks[] ) {
    int blankCount = 0, playerCount = 0;

    for ( MarkType mark : marks ) {
      if ( mark == MarkType.PLAYER )
        playerCount++;
      else if ( mark == MarkType.BLANK )
        blankCount++;
    }

    return (playerCount == 2 && blankCount == 1);
  }

  private int findBlockPosition( MarkType marks[] ) {
    int position = -1;

    for ( int pos = 0; pos < marks.length; pos++ )
      if ( marks[pos] == MarkType.BLANK )
        position = pos;

    return position;
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