package com.jneander.tictactoe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jneander.tictactoe.Mark.MarkType;

public class Game {
  private Mark lastMark;
  private int computerMarkCount;

  private Mark gameBoard[][];
  private List< Mark[] > winningSets;

  public Game() {
    gameBoard = new Mark[3][3];

    resetGameBoard();

    makeWinningSetList();
  }

  private void makeWinningSetList() {
    winningSets = new ArrayList< Mark[] >( 8 );

    for ( int row = 0; row < 3; row++ )
      winningSets.add( gameBoard[row] );

    for ( int col = 0; col < 3; col++ ) {
      Mark columns[] = new Mark[3];

      for ( int row = 0; row < 3; row++ )
        columns[row] = gameBoard[row][col];

      winningSets.add( columns );
    }

    Mark leftDiagonal[] = new Mark[] { gameBoard[0][0], gameBoard[1][1], gameBoard[2][2] };
    Mark rightDiagonal[] = new Mark[] { gameBoard[2][0], gameBoard[1][1], gameBoard[0][2] };

    winningSets.add( leftDiagonal );
    winningSets.add( rightDiagonal );
  }

  private void resetGameBoard() {
    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        gameBoard[row][col] = new Mark( row, col );
  }

  public Mark[][] getGameBoard() {
    return gameBoard;
  }

  public Mark getLastMark() {
    return lastMark;
  }

  public void makePlayerMark( int row, int col ) {
    gameBoard[row][col].setToPlayer();
    lastMark = gameBoard[row][col];
  }

  public void makeComputerMark() {
    Mark nextMark = null;

    nextMark = getWinningMark( MarkType.COMPUTER );
    if ( nextMark == null )
      nextMark = getWinningMark( MarkType.PLAYER );
    if ( nextMark == null && computerShouldBlockAndFork() )
      nextMark = getUnionMark();
    if ( nextMark == null && computerCanFork() )
      nextMark = getBlankCorner();
    if ( nextMark == null && !centerIsMarked() )
      nextMark = gameBoard[1][1];
    if ( nextMark == null )
      nextMark = getBlankCorner();
    if ( nextMark == null )
      nextMark = getBlankEdge();

    gameBoard[nextMark.row][nextMark.col].setToComputer();
    lastMark = nextMark;
    computerMarkCount++;
  }

  private Mark getWinningMark( MarkType markType ) {
    Mark nextMark = null;
    boolean madeMark = false;

    Iterator< Mark[] > winningSetsIterator = winningSets.iterator();

    while ( winningSetsIterator.hasNext() && !madeMark ) {
      Mark[] currentMarkSet = winningSetsIterator.next();

      if ( winningMarkPossibleInSet( currentMarkSet, markType ) ) {
        nextMark = findMarkForBlock( currentMarkSet );
        madeMark = true;
      }
    }

    return nextMark;
  }

  private Mark getUnionMark() {
    Mark nextMark = null;

    if ( isUnion( gameBoard[0][0] ) )
      nextMark = gameBoard[0][0];
    else if ( isUnion( gameBoard[0][2] ) )
      nextMark = gameBoard[0][2];
    else if ( isUnion( gameBoard[2][0] ) )
      nextMark = gameBoard[2][0];
    else if ( isUnion( gameBoard[2][2] ) )
      nextMark = gameBoard[2][2];

    return nextMark;
  }

  private boolean computerShouldBlockAndFork() {
    boolean blockAndFork = false;

    if ( !cornersAreMarked() )
      blockAndFork = (isUnion( gameBoard[0][0] ) || isUnion( gameBoard[0][2] )
          || isUnion( gameBoard[2][0] ) || isUnion( gameBoard[2][2] ));

    return blockAndFork;
  }

  private boolean isUnion( Mark mark ) {
    boolean rowHasPlayerMark = false;
    boolean colHasPlayerMark = false;
    boolean noComputerMarks = true;

    if ( mark.getType() == MarkType.BLANK ) {
      for ( int row = 0; row < 3; row++ ) {
        rowHasPlayerMark |= gameBoard[row][mark.col].getType() == MarkType.PLAYER;
        noComputerMarks &= gameBoard[row][mark.col].getType() != MarkType.COMPUTER;
      }
      for ( int col = 0; col < 3; col++ ) {
        colHasPlayerMark |= gameBoard[mark.row][col].getType() == MarkType.PLAYER;
        noComputerMarks &= gameBoard[mark.row][col].getType() != MarkType.COMPUTER;
      }
    }

    return (rowHasPlayerMark && colHasPlayerMark && noComputerMarks);
  }

  private boolean cornersAreMarked() {
    return gameBoard[0][0].getType() != MarkType.BLANK &&
        gameBoard[2][0].getType() != MarkType.BLANK &&
        gameBoard[0][2].getType() != MarkType.BLANK &&
        gameBoard[2][2].getType() != MarkType.BLANK;
  }

  private boolean centerIsMarked() {
    return gameBoard[1][1].getType() != MarkType.BLANK;
  }

  private boolean computerCanFork() {
    return (computerMarkCount != 0 && !cornersAreMarked());
  }

  private Mark getBlankCorner() {
    Mark blankCorner = null;

    if ( gameBoard[0][0].getType() == MarkType.BLANK )
      blankCorner = gameBoard[0][0];
    else if ( gameBoard[0][2].getType() == MarkType.BLANK )
      blankCorner = gameBoard[0][2];
    else if ( gameBoard[2][0].getType() == MarkType.BLANK )
      blankCorner = gameBoard[2][0];
    else if ( gameBoard[2][2].getType() == MarkType.BLANK )
      blankCorner = gameBoard[2][2];

    return blankCorner;
  }

  private Mark getBlankEdge() {
    Mark blankCorner = null;

    if ( gameBoard[1][0].getType() == MarkType.BLANK )
      blankCorner = gameBoard[0][0];
    else if ( gameBoard[0][1].getType() == MarkType.BLANK )
      blankCorner = gameBoard[0][2];
    else if ( gameBoard[2][1].getType() == MarkType.BLANK )
      blankCorner = gameBoard[2][0];
    else if ( gameBoard[1][2].getType() == MarkType.BLANK )
      blankCorner = gameBoard[2][2];

    return blankCorner;
  }

  private boolean winningMarkPossibleInSet( Mark marks[], MarkType markType ) {
    int blankCount = 0, playerCount = 0;

    for ( Mark mark : marks ) {
      if ( mark.getType() == markType )
        playerCount++;
      else if ( mark.getType() == MarkType.BLANK )
        blankCount++;
    }

    return (playerCount == 2 && blankCount == 1);
  }

  private Mark findMarkForBlock( Mark marks[] ) {
    Mark blockMark = null;

    for ( Mark mark : marks )
      if ( mark.getType() == MarkType.BLANK )
        blockMark = mark;

    return blockMark;
  }

  public boolean isGameOver() {
    boolean gameOver = false;

    Iterator< Mark[] > winningSetsIterator = winningSets.iterator();
    while ( winningSetsIterator.hasNext() && !gameOver ) {
      Mark[] set = winningSetsIterator.next();
      gameOver = (set[0].getType() == set[1].getType() && set[1].getType() == set[2].getType()
          && set[0].getType() != MarkType.BLANK);
    }

    return gameOver;
  }
}