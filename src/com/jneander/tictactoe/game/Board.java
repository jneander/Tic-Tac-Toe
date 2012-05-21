package com.jneander.tictactoe.game;

public class Board {
  private Mark gameBoard[];
  private int blankSpaces;

  private boolean solutionFound;
  private Mark winningMark;

  public final static int winSets[][] = {
      { 0, 1, 2 }, { 3, 4, 5 },
      { 6, 7, 8 }, { 0, 3, 6 },
      { 1, 4, 7 }, { 2, 5, 8 },
      { 0, 4, 8 }, { 2, 4, 6 }
  };

  public Board() {
    gameBoard = new Mark[9];
    blankSpaces = 9;

    for ( int boardIndex = 0; boardIndex < gameBoard.length; boardIndex++ ) {
      gameBoard[boardIndex] = Mark.BLANK;
    }
  }

  public void addMark( int spaceIndex, Mark mark ) {
    if ( gameBoard[spaceIndex] != mark ) {
      gameBoard[spaceIndex] = mark;

      if ( mark == Mark.BLANK ) {
        blankSpaces++;
      } else {
        blankSpaces--;
      }
    }
  }

  public void eraseMark( int spaceIndex ) {
    if ( gameBoard[spaceIndex] != Mark.BLANK ) {
      gameBoard[spaceIndex] = Mark.BLANK;
      blankSpaces++;
    }
  }

  public void reset() {
    for ( int spaceIndex = 0; spaceIndex < gameBoard.length; spaceIndex++)
      eraseMark(spaceIndex);
  }
  
  public int[] getAvailableSpaces() {
    int spaces[] = new int[blankSpaces];
    int spacesIndex = 0;

    for ( int boardIndex = 0; boardIndex < gameBoard.length; boardIndex++ )
      if ( gameBoard[boardIndex] == Mark.BLANK )
        spaces[spacesIndex++] = boardIndex;

    return spaces;
  }

  public Mark getMarkAtIndex( int spaceIndex ) {
    return gameBoard[spaceIndex];
  }

  public boolean hasWinningSolution() {
    this.solutionFound = false;
    this.winningMark = Mark.BLANK;

    for ( int setIndex = 0; setIndex < winSets.length && !solutionFound; setIndex++ )
      checkSpacesForWinningSolution( winSets[setIndex][0], winSets[setIndex][1], winSets[setIndex][2] );

    return solutionFound;
  }

  public Mark getWinningMark() {
    return this.winningMark;
  }

  private void checkSpacesForWinningSolution( int first, int second, int third ) {
    if ( gameBoard[first] == gameBoard[second] && gameBoard[third] == gameBoard[first]
        && gameBoard[first] != Mark.BLANK ) {
      solutionFound = true;
      winningMark = gameBoard[first];
    }
  }
}