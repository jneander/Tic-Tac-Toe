package com.jneander.tictactoe.game;

public class Board {
  private Mark2 gameBoard[];
  private int blankSpaces;

  public Board() {
    gameBoard = new Mark2[9];
    blankSpaces = 9;

    for ( int boardIndex = 0; boardIndex < gameBoard.length; boardIndex++ ) {
      gameBoard[boardIndex] = Mark2.BLANK;  
    }
  }
  
  public void addMark( int spaceIndex, Mark2 mark ) {
    if ( gameBoard[spaceIndex] != mark ) {
      gameBoard[spaceIndex] = mark;

      if ( mark == Mark2.BLANK ) {
        blankSpaces++;
      } else {
        blankSpaces--;
      }
    }
  }
  
  public int[] getAvailableSpaces() {
    int spaces[] = new int[blankSpaces];
    int spacesIndex = 0;

    for ( int boardIndex = 0; boardIndex < gameBoard.length; boardIndex++ )
      if ( gameBoard[boardIndex] == Mark2.BLANK )
        spaces[spacesIndex++] = boardIndex;

    return spaces;
  }
  
  public Mark2 getMarkAtIndex( int spaceIndex ) {
    return gameBoard[spaceIndex];
  }
}