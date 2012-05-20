package com.jneander.tictactoe.game;

public class Board {
  private Mark2 gameBoard[];
  private int blankSpaces;

  private boolean solutionFound;
  private Mark2 winningMark;

  public final static int winSets[][] = {
      { 0, 1, 2 }, { 3, 4, 5 },
      { 6, 7, 8 }, { 0, 3, 6 },
      { 1, 4, 7 }, { 2, 5, 8 },
      { 0, 4, 8 }, { 2, 4, 6 }
  };

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

  public boolean hasWinningSolution() {
    this.solutionFound = false;
    this.winningMark = Mark2.BLANK;

    for ( int setIndex = 0; setIndex < winSets.length && !solutionFound; setIndex++ )
      checkSpacesForWinningSolution( winSets[setIndex][0], winSets[setIndex][1], winSets[setIndex][2] );

    return solutionFound;
  }
  
  public Mark2 getWinningMark() {
    return this.winningMark;
  }

  private void checkSpacesForWinningSolution( int first, int second, int third ) {
    if ( gameBoard[first] == gameBoard[second] && gameBoard[third] == gameBoard[first]
        && gameBoard[first] != Mark2.BLANK ) {
      solutionFound = true;
      winningMark = gameBoard[first];
    }
  }
}