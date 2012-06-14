package com.jneander.tictactoe.game;

public class Board {
  private Mark gameBoard[];
  private int blankSpaces;

  private boolean solutionFound = false;
  private Mark winningMark = Mark.BLANK;

  private int winningSets[][];
  private int boardSize;

  public Board( int boardSize ) {
    this.boardSize = boardSize;

    gameBoard = new Mark[boardSize * boardSize];
    blankSpaces = boardSize * boardSize;

    for ( int boardIndex = 0; boardIndex < gameBoard.length; boardIndex++ )
      gameBoard[boardIndex] = Mark.BLANK;

    winningSets = generateWinningSets( boardSize );
  }

  public void reset() {
    for ( int spaceIndex = 0; spaceIndex < gameBoard.length; spaceIndex++ )
      eraseMark( spaceIndex );
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

  public Mark getWinningMark() {
    return this.winningMark;
  }

  public boolean hasWinningSolution() {
    this.solutionFound = false;
    this.winningMark = Mark.BLANK;

    checkSpacesForWinningSolution();

    return solutionFound;
  }

  public static int[][] generateWinningSets( int boardSize ) {
    int[][] winSets = new int[boardSize * 2 + 2][boardSize];
    int winSetsAdded = 0;

    int[] diagR = new int[boardSize];
    int[] diagL = new int[boardSize];

    for ( int rowCol = 0; rowCol < boardSize; rowCol++ ) {
      int[] rowSet = new int[boardSize];
      int[] colSet = new int[boardSize];

      for ( int offset = 0; offset < boardSize; offset++ ) {
        rowSet[offset] = rowCol * boardSize + offset;
        colSet[offset] = boardSize * offset + rowCol;
      }

      winSets[winSetsAdded++] = rowSet;
      winSets[winSetsAdded++] = colSet;

      diagR[rowCol] = rowCol * (boardSize + 1);
      diagL[rowCol] = (boardSize - 1) + rowCol * (boardSize - 1);
    }

    winSets[winSetsAdded++] = diagR;
    winSets[winSetsAdded++] = diagL;

    return winSets;
  }

  private void checkSpacesForWinningSolution() {
    for ( int setIndex = 0; setIndex < winningSets.length && !solutionFound; setIndex++ ) {
      Mark markToCompare = gameBoard[winningSets[setIndex][0]];
      boolean allMarksMatch = markToCompare != Mark.BLANK;

      for ( int markIndex = 1; markIndex < boardSize && allMarksMatch; markIndex++ )
        allMarksMatch &= gameBoard[winningSets[setIndex][markIndex]] == markToCompare;

      this.solutionFound = allMarksMatch;

      if ( solutionFound )
        this.winningMark = markToCompare;
    }
  }
}