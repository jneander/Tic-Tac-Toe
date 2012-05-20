package com.jneander.tictactoe.game;

public class Board {
  private Mark2 gameBoard[];

  public Board() {
    gameBoard = new Mark2[9];

    for ( int boardIndex = 0; boardIndex < gameBoard.length; boardIndex++ ) {
      gameBoard[boardIndex] = Mark2.BLANK;
    }
  }
}