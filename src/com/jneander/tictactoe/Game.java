package com.jneander.tictactoe;

import com.jneander.tictactoe.Mark.MarkType;

public class Game {
  private Mark lastMark;
  private int playerMarks;

  private Mark gameBoard[][];

  public Game() {
    gameBoard = new Mark[3][3];

    resetGameBoard();
    playerMarks = 0;
  }

  private void resetGameBoard() {
    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        gameBoard[row][col] = new Mark( row, col, MarkType.BLANK );
  }

  public Mark[][] getGameBoard() {
    return gameBoard;
  }

  public Mark getLastMark() {
    return lastMark;
  }

  public void makePlayerMark( Mark mark ) {
    gameBoard[mark.row][mark.col] = mark;
    lastMark = mark;
    playerMarks++;
  }

  public void makeComputerMark() {
    Mark mark = new Mark( 1, 1, MarkType.COMPUTER );
    boolean madeMark = false;

    if ( playerMarks == 1 ) {
      if ( isCenterMark( lastMark ) ) {
        mark = new Mark( 0, 0, MarkType.COMPUTER );
      } else {
        mark = new Mark( 1, 1, MarkType.COMPUTER );
      }
    } else {
      for ( int row = 0; (row < gameBoard.length) && !madeMark; row++ ) {
        if ( setNeedsBlock( gameBoard[row] ) )
          mark = new Mark( row, findBlockPosition( gameBoard[row] ), MarkType.COMPUTER );
      }
      for ( int col = 0; (col < gameBoard[0].length) && !madeMark; col++ ) {
        Mark colMarks[] = getGameBoardColumn( col );
        if ( setNeedsBlock( colMarks ) )
          mark = new Mark( findBlockPosition( colMarks ), col, MarkType.COMPUTER );
      }
      if ( !madeMark ) {
        Mark diagMarks[] = new Mark[] { gameBoard[0][0], gameBoard[1][1], gameBoard[2][2] };
        if ( setNeedsBlock( diagMarks ) ) {
          int pos = findBlockPosition( diagMarks );
          mark = new Mark( pos, pos, MarkType.COMPUTER );
        }
      }
      if ( !madeMark ) {
        Mark diagMarks[] = new Mark[] { gameBoard[0][2], gameBoard[1][1], gameBoard[2][0] };
        if ( setNeedsBlock( diagMarks ) ) {
          int pos = findBlockPosition( diagMarks );
          mark = new Mark( pos, gameBoard[0].length - pos - 1, MarkType.COMPUTER );
        }
      }
    }

    gameBoard[mark.row][mark.col] = mark;
    lastMark = mark;
  }

  private Mark[] getGameBoardColumn( int col ) {
    Mark marks[] = new Mark[gameBoard.length];

    for ( int row = 0; row < gameBoard.length; row++ )
      marks[row] = gameBoard[row][col];

    return marks;
  }

  private boolean setNeedsBlock( Mark marks[] ) {
    int blankCount = 0, playerCount = 0;

    for ( Mark mark : marks ) {
      if ( mark.markType == MarkType.PLAYER )
        playerCount++;
      else if ( mark.markType == MarkType.BLANK )
        blankCount++;
    }

    return (playerCount == 2 && blankCount == 1);
  }

  private int findBlockPosition( Mark marks[] ) {
    int position = -1;

    for ( int pos = 0; pos < marks.length; pos++ )
      if ( marks[pos].markType == MarkType.BLANK )
        position = pos;

    return position;
  }

  private boolean isCenterMark( Mark mark ) {
    return (mark.row == 1 && mark.col == 1);
  }
}