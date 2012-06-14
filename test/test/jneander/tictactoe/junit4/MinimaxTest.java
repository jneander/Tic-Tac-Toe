package test.jneander.tictactoe.junit4;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.util.Minimax;

public class MinimaxTest {
  private Board board;

  @Before
  public void setUp() {
    board = new Board(3);
  }

  @Test
  public void scoreHighOnWinningMove() {
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 2, Mark.PLAYER );
    board.addMark( 3, Mark.COMPUTER );
    board.addMark( 5, Mark.PLAYER );
    board.addMark( 6, Mark.COMPUTER );
    
    assertEquals(1, Minimax.minimax(board, Mark.PLAYER));
  }

  @Test
  public void scoreLowOnLosingMove() {
    board.addMark( 2, Mark.PLAYER );
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 5, Mark.PLAYER );
    board.addMark( 3, Mark.COMPUTER );
    
    assertEquals(-1, Minimax.minimax(board, Mark.PLAYER));
  }

  @Test
  public void scoreMidOnTyingMove() {
    board.addMark( 4, Mark.PLAYER );
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 6, Mark.PLAYER );
    board.addMark( 2, Mark.COMPUTER );
    board.addMark( 1, Mark.PLAYER );
    board.addMark( 7, Mark.COMPUTER );
    board.addMark( 5, Mark.PLAYER );
    board.addMark( 3, Mark.COMPUTER );
    
    assertEquals(0, Minimax.minimax(board, Mark.PLAYER));
  }
}