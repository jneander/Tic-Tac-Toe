package com.jneander.tictactoe.junit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Game;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.game.Mark.MarkType;

public class GameTest {
  private Game game;
  private Mark gameBoard[][];

  @Before
  public void setUp() throws Exception {
    game = new Game();
  }

  @Test
  public void allSpacesAreOpen() {
    getGameBoard();

    for ( int row = 0; row < gameBoard.length; row++ )
      for ( int col = 0; col < gameBoard[row].length; col++ )
        assertTrue( gameBoard[row][col].getType() == MarkType.BLANK );
  }

  @Test
  public void lastMarkWasMade() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 0, 0 );
    Mark lastMark = game.getLastMark();

    assertNotNull( lastMark );
    assertNotNull( lastMark.col );
    assertNotNull( lastMark.row );

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  @Test
  public void playerCanMakeMark() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 0, 0 );
    getGameBoard();

    assertTrue( gameBoard[0][0].getType() == MarkType.PLAYER );
    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  @Test
  public void computerCanMakeMark() {
    allSpacesAreOpen();

    game.makeComputerMark();
    getGameBoard();

    assertTrue( xNumberOfMarksHaveBeenMade( 1 ) );
  }

  @Test
  public void computerMarksCenterAfterPlayerMarksCornerFirst() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 0, 0 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertFalse( lastMark.row == 0 && lastMark.col == 0 );
    assertFalse( lastMark.row == 1 && lastMark.col == 2 );
    assertFalse( lastMark.row == 2 && lastMark.col == 1 );
  }

  @Test
  public void computerMarksCenterAfterPlayerMarksEdgeFirst() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 1, 0 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertFalse( lastMark.row == 1 && lastMark.col == 0 );
    assertTrue( lastMark.row == 1 && lastMark.col == 1 );
  }

  @Test
  public void computerMarksCornerAfterPlayerMarksCenterFirst() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 1, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertFalse( lastMark.row == 1 && lastMark.col == 1 );
    assertTrue( lastMark.row == 0 && lastMark.col == 0 );
  }

  @Test
  public void computerBlocksConsecutiveRowPlayerMarks() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 0, 0 );
    game.makePlayerMarkAtPosition( 0, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 );
    assertTrue( lastMark.col == 2 );
  }

  @Test
  public void computerBlocksConsecutiveColPlayerMarks() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 0, 0 );
    game.makePlayerMarkAtPosition( 1, 0 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 2 );
    assertTrue( lastMark.col == 0 );
  }

  @Test
  public void computerBlocksConsecutiveRightDiagonalPlayerMarks() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 0, 0 );
    game.makePlayerMarkAtPosition( 1, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 2 );
    assertTrue( lastMark.col == 2 );
  }

  @Test
  public void computerBlocksConsecutiveLeftDiagonalPlayerMarks() {
    allSpacesAreOpen();

    game.makePlayerMarkAtPosition( 2, 0 );
    game.makePlayerMarkAtPosition( 1, 1 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 );
    assertTrue( lastMark.col == 2 );
  }

  @Test
  public void gameIsOver() {
    game.makePlayerMarkAtPosition( 0, 0 );
    game.makePlayerMarkAtPosition( 1, 0 );

    assertFalse( game.isGameOver() );

    game.makePlayerMarkAtPosition( 2, 0 );

    assertTrue( game.isGameOver() );
  }

  @Test
  public void computerWillFork() {
    game.makeComputerMark();
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 || lastMark.row == 2 );
    assertTrue( lastMark.col == 0 || lastMark.col == 2 );
  }

  @Test
  public void computerWillBlockAndFork() {
    game.makePlayerMarkAtPosition( 0, 1 );
    game.makeComputerMark();
    game.makePlayerMarkAtPosition( 2, 2 );
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 0 );
    assertTrue( lastMark.col == 2 );
  }

  @Test
  public void computerWillMakeWinningMark() {
    game.makeComputerMark();
    game.makeComputerMark();
    game.makeComputerMark();
    Mark lastMark = game.getLastMark();

    assertTrue( lastMark.row == 2 );
    assertTrue( lastMark.col == 2 );
  }

  @Test
  public void computerWillNeverLoseWhenGoingFirst() {
    int markIndices[] = new int[4];
    int runCount = 0;

    for ( markIndices[0] = 0; markIndices[0] < 9; markIndices[0]++ ) {
      for ( markIndices[1] = 0; markIndices[1] < 9; markIndices[1]++ ) {
        for ( markIndices[2] = 0; markIndices[2] < 9; markIndices[2]++ ) {
          for ( markIndices[3] = 0; markIndices[3] < 9; markIndices[3]++ ) {
            runCount++;
            if ( integersAreUnique( markIndices ) ) {
              game = new Game();
              boolean terminate = false;
              Mark gameBoard[][] = game.getGameBoard();

              for ( int turn = 0; turn < markIndices.length && !game.isGameOver() && !terminate; turn++ ) {
                game.makeComputerMark();

                if ( !game.isGameOver() ) {
                  int row = markIndices[turn] % 3;
                  int col = markIndices[turn] / 3;

                  if ( gameBoard[row][col].getType() != MarkType.BLANK )
                    terminate = true;
                  else
                    game.makePlayerMarkAtPosition( row, col );
                }
              }

              if ( game.isGameOver() )
                assertTrue( game.getWinner() != MarkType.PLAYER );
            }
          }
        }
      }
    }

    assertTrue( runCount == 6561 );
  }

  @Test
  public void testComputerWillNeverLoseWhenGoingSecond() {
    int markIndices[] = new int[5];
    int runCount = 0;

    for ( markIndices[0] = 0; markIndices[0] < 9; markIndices[0]++ ) {
      for ( markIndices[1] = 0; markIndices[1] < 9; markIndices[1]++ ) {
        for ( markIndices[2] = 0; markIndices[2] < 9; markIndices[2]++ ) {
          for ( markIndices[3] = 0; markIndices[3] < 9; markIndices[3]++ ) {
            for ( markIndices[4] = 0; markIndices[4] < 9; markIndices[4]++ ) {
              runCount++;
              if ( integersAreUnique( markIndices ) ) {
                game = new Game();
                boolean terminate = false;
                Mark gameBoard[][] = game.getGameBoard();

                for ( int turn = 0; turn < markIndices.length && !game.isGameOver() && !terminate; turn++ ) {
                  int row = markIndices[turn] % 3;
                  int col = markIndices[turn] / 3;

                  if ( gameBoard[row][col].getType() != MarkType.BLANK )
                    terminate = true;
                  else
                    game.makePlayerMarkAtPosition( row, col );

                  if ( !game.isGameOver() )
                    game.makeComputerMark();
                }

                if ( game.isGameOver() )
                  assertTrue( game.getWinner() != MarkType.PLAYER );
              }
            }
          }
        }
      }
    }

    assertTrue( runCount == 59049 );
  }

  private boolean integersAreUnique( int integers[] ) {
    boolean areUnique = true;

    for ( int first = 0; first < integers.length - 1; first++ )
      for ( int second = first + 1; second < integers.length; second++ )
        areUnique &= first != second;

    return areUnique;
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