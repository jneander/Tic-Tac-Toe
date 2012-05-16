package com.jneander.tictactoe.test;

import junit.framework.TestCase;

import com.jneander.tictactoe.Game;

public class MainTest extends TestCase {
  private Game game;
  private int gameBoard[][];

  protected void setUp() throws Exception {
    super.setUp();

    game = new Game();
  }

  public void testAllSpacesOpen() {
    getGameBoard();

    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        assertTrue( gameBoard[row][col] == -1 );
  }

  public void testPlayerCanMakeMark() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    getGameBoard();

    assertTrue( gameBoard[0][0] == 1 );
  }

  public void testComputerCanMakeMark() {
    testAllSpacesOpen();

    game.makeComputerMark();
    getGameBoard();

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }
  
  private void getGameBoard() {
    gameBoard = game.getGameBoard();
  }

  private boolean xNumberOfMarksHaveBeenMade( int matchValue ) {
    int marksMade = 0;

    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        if ( gameBoard[row][col] != -1 )
          marksMade++;

    return (marksMade == matchValue);
  }
}