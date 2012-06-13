package com.jneander.tictactoe.game;

import com.jneander.tictactoe.util.Minimax;

public class Game {
  private Board board;
  private Computer computer;

  private boolean gameOver = false;
  private Mark winner = Mark.BLANK;

  public Game() {
    board = new Board();
    computer = new ImpossibleComputer( board );
  }

  public void reset() {
    board.reset();
    this.gameOver = false;
    this.winner = Mark.BLANK;
  }

  public boolean isGameOver() {
    return this.gameOver;
  }

  public Mark getWinner() {
    return this.winner;
  }

  public boolean positionIsMarked( int spaceIndex ) {
    return (board.getMarkAtIndex( spaceIndex ) != Mark.BLANK);
  }

  public void makePlayerMarkAtPosition( int spaceIndex ) {
    if ( !positionIsMarked( spaceIndex ) ) {
      board.addMark( spaceIndex, Mark.PLAYER );
      checkForGameOver();
    }
  }

  public int makeComputerMark() {
    int bestSpace = computer.getNextMarkPosition();
    board.addMark( bestSpace, Mark.COMPUTER );
    checkForGameOver();
    return bestSpace;
  }

  private void checkForGameOver() {
    if ( board.hasWinningSolution() || board.getAvailableSpaces().length == 0 ) {
      this.winner = board.getWinningMark();
      this.gameOver = true;
    }
  }
}