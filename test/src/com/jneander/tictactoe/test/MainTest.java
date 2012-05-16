package com.jneander.tictactoe.test;

import junit.framework.TestCase;

import com.jneander.tictactoe.Game;
import com.jneander.tictactoe.Mark;
import com.jneander.tictactoe.Mark.MarkType;

public class MainTest extends TestCase {
  private Game game;
  private Mark gameBoard[][];

  protected void setUp() throws Exception {
    super.setUp();

    game = new Game();
  }

  public void testAllSpacesOpen() {
    getGameBoard();

    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        assertTrue( gameBoard[row][col].getType() == MarkType.BLANK );
  }

  public void testLastMarkWasMade() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    Mark lastMark = game.getLastMark();

    assertNotNull( lastMark );
    assertNotNull( lastMark.col );
    assertNotNull( lastMark.row );

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  public void testPlayerCanMakeMark() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    getGameBoard();

    assertTrue( gameBoard[0][0].getType() == MarkType.PLAYER );
    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  public void testComputerCanMakeMark() {
    testAllSpacesOpen();

    game.makeComputerMark();
    getGameBoard();

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  public void testComputerAnswersPlayerFirstCornerMark() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertFalse( lastMark.row == 0 && lastMark.col == 0 );
    assertFalse( lastMark.row == 1 && lastMark.col == 2 );
    assertFalse( lastMark.row == 2 && lastMark.col == 1 );
  }

  public void testComputerAnswersPlayerFirstEdgeMark() {
    testAllSpacesOpen();

    game.makePlayerMark( 1, 0 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertFalse( lastMark.row == 1 && lastMark.col == 0 );
    assertTrue( lastMark.row == 1 && lastMark.col == 1 );
  }

  public void testComputerAnswersPlayerFirstCenterMark() {
    testAllSpacesOpen();

    game.makePlayerMark( 1, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertFalse( lastMark.row == 1 && lastMark.col == 1 );
    assertTrue( lastMark.row == 0 && lastMark.col == 0 );
  }

  public void testComputerBlocksConsecutiveRowPlayerMarks() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    game.makePlayerMark( 0, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 );
    assertTrue( lastMark.col == 2 );
  }

  public void testComputerBlocksConsecutiveColPlayerMarks() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    game.makePlayerMark( 1, 0 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 2 );
    assertTrue( lastMark.col == 0 );
  }

  public void testComputerBlocksConsecutiveRightDiagonalPlayerMarks() {
    testAllSpacesOpen();

    game.makePlayerMark( 0, 0 );
    game.makePlayerMark( 1, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 2 );
    assertTrue( lastMark.col == 2 );
  }

  public void testComputerBlocksConsecutiveLeftDiagonalPlayerMarks() {
    testAllSpacesOpen();

    game.makePlayerMark( 2, 0 );
    game.makePlayerMark( 1, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 );
    assertTrue( lastMark.col == 2 );
  }

  public void testGameOver() {
    game.makePlayerMark( 0, 0 );
    game.makePlayerMark( 1, 0 );

    assertFalse( game.isGameOver() );

    game.makePlayerMark( 2, 0 );

    assertTrue( game.isGameOver() );
  }

  public void testComputerWillFork() {
    game.makeComputerMark();
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 || lastMark.row == 2 );
    assertTrue( lastMark.col == 0 || lastMark.col == 2 );
  }

  public void testComputerWillBlockAndFork() {
    game.makePlayerMark( 0, 1 );
    game.makeComputerMark();
    game.makePlayerMark( 2, 2 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 );
    assertTrue( lastMark.col == 2 );
  }

  public void testComputerWillMakeWinningMark() {
    game.makeComputerMark();
    game.makeComputerMark();
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 2 );
    assertTrue( lastMark.col == 2 );
  }

  private void getGameBoard() {
    gameBoard = game.getGameBoard();
  }

  private boolean xNumberOfMarksHaveBeenMade( int matchValue ) {
    int marksMade = 0;

    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        if ( gameBoard[row][col].getType() != MarkType.BLANK )
          marksMade++;

    return (marksMade == matchValue);
  }

  private void printGameBoard() {
    getGameBoard();

    for ( int row = 0; row < 3; row++ ) {
      System.out.printf( " %c | %c | %c\n",
          getMarkCharacter( gameBoard[row][0] ),
          getMarkCharacter( gameBoard[row][1] ),
          getMarkCharacter( gameBoard[row][2] )
          );
      if ( row != 2 )
        System.out.println( "------------" );
    }
  }

  private char getMarkCharacter( Mark mark ) {
    char character = mark.getType() == MarkType.COMPUTER ? 'X' : ' ';
    character = mark.getType() == MarkType.PLAYER ? 'O' : character;

    return character;

  }
}