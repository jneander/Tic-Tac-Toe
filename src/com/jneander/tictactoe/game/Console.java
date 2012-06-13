package com.jneander.tictactoe.game;

public interface Console {
  public void reportGameTied();

  public void reportGameLost();

  public void reportGameWon();

  public void reportIllegalMove();

  public void displayComputerMark( int position );
}