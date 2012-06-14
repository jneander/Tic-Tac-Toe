package test.jneander.tictactoe.junit4;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.util.Minimax;

public class MinimaxTest {
  private Board board3x3;

  @Before
  public void setUp() {
    board3x3 = new Board(3);
  }

  @Test
  public void scoreHighOnWinningMove() {
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 2, Mark.PLAYER );
    board3x3.addMark( 3, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.PLAYER );
    board3x3.addMark( 6, Mark.COMPUTER );
    
    assertEquals(1, Minimax.minimax(board3x3, Mark.PLAYER));
  }

  @Test
  public void scoreLowOnLosingMove() {
    board3x3.addMark( 2, Mark.PLAYER );
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.PLAYER );
    board3x3.addMark( 3, Mark.COMPUTER );
    
    assertEquals(-1, Minimax.minimax(board3x3, Mark.PLAYER));
  }

  @Test
  public void scoreMidOnTyingMove() {
    board3x3.addMark( 4, Mark.PLAYER );
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 6, Mark.PLAYER );
    board3x3.addMark( 2, Mark.COMPUTER );
    board3x3.addMark( 1, Mark.PLAYER );
    board3x3.addMark( 7, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.PLAYER );
    board3x3.addMark( 3, Mark.COMPUTER );
    
    assertEquals(0, Minimax.minimax(board3x3, Mark.PLAYER));
  }
}