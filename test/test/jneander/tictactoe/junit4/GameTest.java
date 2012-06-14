package test.jneander.tictactoe.junit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Console;
import com.jneander.tictactoe.game.Game;
import com.jneander.tictactoe.game.Mark;

public class GameTest {
  private Game game;
  private final int boardSize = 3;
  private final int indexCount = boardSize * boardSize;
  private Console console;

  @Before
  public void setUp() {
    game = new Game(3);
    console = new Console() {

      @Override
      public void reportGameTied() {}

      @Override
      public void reportGameLost() {}

      @Override
      public void reportGameWon() {}

      @Override
      public void reportIllegalMove() {}

      @Override
      public void displayComputerMark( int position ) {}
    };
    game.setConsole( console );
  }

  @Test
  public void newGameHasNoWinner() {
    assertEquals( Mark.BLANK, game.getWinner() );
  }

  @Test
  public void newGameIsNotOver() {
    assertFalse( game.isGameOver() );
  }

  @Test
  public void newGameHasNoMarks() {
    for ( int spaceIndex = 0; spaceIndex < indexCount; spaceIndex++ )
      assertFalse( game.positionIsMarked( spaceIndex ) );
  }

  @Test
  public void canMakePlayerMark() {
    game.makePlayerMarkAtPosition( 0 );
    assertTrue( game.positionIsMarked( 0 ) );

    game.makePlayerMarkAtPosition( 8 );
    assertTrue( game.positionIsMarked( 8 ) );
  }

  @Test
  public void gameCanReset() {
    game.makePlayerMarkAtPosition( 0 );
    game.reset();
    game.reset(); // second reset also resets turn order
    assertFalse( game.positionIsMarked( 0 ) );
  }

  @Test
  public void generateWinSets() {
    int[][] knownWinSets3x3 = {
        { 0, 1, 2 }, { 0, 3, 6 },
        { 3, 4, 5 }, { 1, 4, 7 },
        { 6, 7, 8 }, { 2, 5, 8 },
        { 0, 4, 8 }, { 2, 4, 6 }
    };

    int[][] knownWinSets4x4 = {
        { 0, 1, 2, 3 }, { 0, 4, 8, 12 },
        { 4, 5, 6, 7 }, { 1, 5, 9, 13 },
        { 8, 9, 10, 11 }, { 2, 6, 10, 14 },
        { 12, 13, 14, 15 }, { 3, 7, 11, 15 },
        { 0, 5, 10, 15 }, { 3, 6, 9, 12 }
    };

    int[][] boardWinSets3x3 = Board.generateWinningSets( 3 );
    int[][] boardWinSets4x4 = Board.generateWinningSets( 4 );

    for ( int setIndex = 0; setIndex < knownWinSets3x3.length; setIndex++ )
      assertArrayEquals( knownWinSets3x3[setIndex], boardWinSets3x3[setIndex] );

    for ( int setIndex = 0; setIndex < knownWinSets4x4.length; setIndex++ )
      assertArrayEquals( knownWinSets4x4[setIndex], boardWinSets4x4[setIndex] );
  }
}