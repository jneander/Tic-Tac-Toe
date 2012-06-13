package com.jneander.tictactoe.game;

public class Game {
  private Console console;
  private Board board;
  private Computer computer;

  private boolean playerFirst = true;
  private boolean gameOver = false;
  private Mark winner = Mark.BLANK;

  public Game() {
    board = new Board();
    computer = new ImpossibleComputer( board );
  }

  public void setConsole( Console console ) {
    this.console = console;
  }

  public void reset() {
    board.reset();
    this.playerFirst = !this.playerFirst;
    this.gameOver = false;
    this.winner = Mark.BLANK;

    if ( !playerFirst )
      makeComputerMark();
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

      if ( !gameOver )
        makeComputerMark();
    }
  }

  private void makeComputerMark() {
    int bestSpace = computer.getNextMarkPosition();
    board.addMark( bestSpace, Mark.COMPUTER );
    console.displayComputerMark( bestSpace );
    checkForGameOver();
  }

  private void checkForGameOver() {
    if ( board.hasWinningSolution() || board.getAvailableSpaces().length == 0 ) {
      this.winner = board.getWinningMark();
      this.gameOver = true;
    }

    if ( gameOver ) {
      if ( this.winner == Mark.COMPUTER )
        console.reportGameLost();
      else if ( this.winner == Mark.BLANK )
        console.reportGameTied();
      else
        console.reportGameWon();
    }
  }
}