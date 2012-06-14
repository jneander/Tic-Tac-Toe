package test.jneander.tictactoe.junit4;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.util.Minimax;

public class MinimaxTest {
  private Board board3x3;
  private Board board4x4;

  @Before
  public void setUp() {
    board3x3 = new Board( 3 );
    board4x4 = new Board( 4 );
  }

  @Test
  public void scoreHighOnWinningMove3x3() {
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 2, Mark.PLAYER );
    board3x3.addMark( 3, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.PLAYER );
    board3x3.addMark( 6, Mark.COMPUTER );

    assertEquals( 1, Minimax.minimax( board3x3, Mark.PLAYER ) );
  }

  @Test
  public void scoreHighOnWinningMove4x4() {
    board4x4.addMark( 0, Mark.COMPUTER );
    board4x4.addMark( 1, Mark.PLAYER );
    board4x4.addMark( 4, Mark.COMPUTER );
    board4x4.addMark( 5, Mark.PLAYER );
    board4x4.addMark( 8, Mark.COMPUTER );
    board4x4.addMark( 9, Mark.PLAYER );
    board4x4.addMark( 12, Mark.COMPUTER );

    assertEquals( 1, Minimax.minimax( board4x4, Mark.PLAYER ) );
  }

  @Test
  public void scoreLowOnLosingMove3x3() {
    board3x3.addMark( 2, Mark.PLAYER );
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.PLAYER );
    board3x3.addMark( 3, Mark.COMPUTER );

    assertEquals( -1, Minimax.minimax( board3x3, Mark.PLAYER ) );
  }

  @Test
  public void scoreLowOnLosingMove4x4() {
    board4x4.addMark( 0, Mark.PLAYER );
    board4x4.addMark( 1, Mark.COMPUTER );
    board4x4.addMark( 4, Mark.PLAYER );
    board4x4.addMark( 5, Mark.COMPUTER );
    board4x4.addMark( 8, Mark.PLAYER );
    board4x4.addMark( 9, Mark.COMPUTER );

    assertEquals( -1, Minimax.minimax( board4x4, Mark.PLAYER ) );
  }

  @Test
  public void scoreMidOnTyingMove3x3() {
    board3x3.addMark( 4, Mark.PLAYER );
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 6, Mark.PLAYER );
    board3x3.addMark( 2, Mark.COMPUTER );
    board3x3.addMark( 1, Mark.PLAYER );
    board3x3.addMark( 7, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.PLAYER );
    board3x3.addMark( 3, Mark.COMPUTER );

    assertEquals( 0, Minimax.minimax( board3x3, Mark.PLAYER ) );
  }
}