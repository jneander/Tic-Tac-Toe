package com.jneander.tictactoe.test;

import junit.framework.TestCase;

import com.jneander.tictactoe.Game;
import com.jneander.tictactoe.Mark;

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

  public void testLastMoveWasMade() {
    testAllSpacesOpen();

    game.makePlayerMark( new Mark( 0, 0 ) );
    Mark lastMove = game.getLastMove();

    assertNotNull( lastMove );
    assertNotNull( lastMove.col );
    assertNotNull( lastMove.row );

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  public void testPlayerCanMakeMark() {
    testAllSpacesOpen();

    game.makePlayerMark( new Mark( 0, 0 ) );
    getGameBoard();

    assertTrue( gameBoard[0][0] == 1 );
    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  public void testComputerCanMakeMark() {
    testAllSpacesOpen();

    game.makeComputerMark();
    getGameBoard();

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  public void testComputerAnswersPlayerFirstCornerMove() {
    testAllSpacesOpen();

    game.makePlayerMark( new Mark( 0, 0 ) );
    game.makeComputerMark();
    Mark lastMove = game.getLastMove();

    assertFalse( lastMove.row == 0 && lastMove.col == 0 );
    assertFalse( lastMove.row == 1 && lastMove.col == 2 );
    assertFalse( lastMove.row == 2 && lastMove.col == 1 );
  }

  public void testComputerAnswersPlayerFirstEdgeMove() {
    testAllSpacesOpen();

    game.makePlayerMark( new Mark( 1, 0 ) );
    game.makeComputerMark();
    Mark lastMove = game.getLastMove();

    assertFalse( lastMove.row == 1 && lastMove.col == 0 );
    assertTrue( lastMove.row == 1 && lastMove.col == 1 );
  }

  public void testComputerAnswersPlayerFirstCenterMove() {
    testAllSpacesOpen();

    game.makePlayerMark( new Mark( 1, 1 ) );
    game.makeComputerMark();
    Mark lastMove = game.getLastMove();

    assertFalse( lastMove.row == 1 && lastMove.col == 1 );
    assertTrue( lastMove.row == 0 && lastMove.col == 0 );
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