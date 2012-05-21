package com.jneander.tictactoe.game;

import com.jneander.tictactoe.util.Minimax;

public class Game2 {
  private Board board;

  private boolean gameOver;
  private Mark2 winner;

  public Game2() {
    board = new Board();
  }

  public void reset() {
    board.reset();
    this.gameOver = false;
    this.winner = Mark2.BLANK;
  }

  public boolean isGameOver() {
    return this.gameOver;
  }

  public Mark2 getWinner() {
    return this.winner;
  }

  public boolean positionIsMarked( int spaceIndex ) {
    return (board.getMarkAtIndex( spaceIndex ) != Mark2.BLANK);
  }

  public void makePlayerMarkAtPosition( int spaceIndex ) {
    if ( !positionIsMarked( spaceIndex ) ) {
      board.addMark( spaceIndex, Mark2.PLAYER );
      checkForGameOver();
    }
  }

  public int makeComputerMark() {
    int bestSpace = getBestSpaceForComputer();
    board.addMark( bestSpace, Mark2.COMPUTER );
    checkForGameOver();
    return bestSpace;
  }

  private void checkForGameOver() {
    if ( board.hasWinningSolution() || board.getAvailableSpaces().length == 0 ) {
      this.winner = board.getWinningMark();
      this.gameOver = true;
    }
  }

  private int getBestSpaceForComputer() {
    int score = 0;
    int[] availableSpaces = board.getAvailableSpaces();
    int bestIndex = 0;

    if ( board.hasWinningSolution() )
      score = ((board.getWinningMark() == Mark2.COMPUTER) ? 1 : -1);
    else if ( availableSpaces.length != 0 ) {
      for ( int availableIndex = 0; availableIndex < availableSpaces.length; availableIndex++ ) {
        int nextScore = getChildBoardScore( availableSpaces[availableIndex] );

        if ( nextScore > score || availableIndex == 0 ) {
          score = nextScore;
          bestIndex = availableSpaces[availableIndex];
        }
      }
    }

    return bestIndex;
  }

  private int getChildBoardScore( int spaceIndex ) {
    board.addMark( spaceIndex, Mark2.COMPUTER );
    int childScore = Minimax.minimax( board, Mark2.PLAYER );
    board.eraseMark( spaceIndex );

    return childScore;
  }
}