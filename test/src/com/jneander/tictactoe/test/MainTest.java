package com.jneander.tictactoe.test;

import junit.framework.TestCase;

import com.jneander.tictactoe.Game;

public class MainTest extends TestCase {
  private Game game;

  protected void setUp() throws Exception {
    super.setUp();

    game = new Game();
  }

  public void testAllSpacesOpen() {
    int spaces[][] = game.getGameBoard();

    for ( int row = 0; row < spaces.length; row++ )
      for ( int col = 0; col < spaces[row].length; col++ )
        assertTrue( spaces[row][col] == -1 );
  }
}