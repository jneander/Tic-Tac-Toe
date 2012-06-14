package test.jneander.tictactoe.junit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;

public class BoardTest {
  private Board board;

  @Before
  public void setUp() {
    board = new Board(3);
  }
  
  @Test
  public void canGetAvailableSpaces() {
    assertEquals(9, board.getAvailableSpaces().length);
    
    board.addMark( 0, Mark.PLAYER );
    assertEquals(8, board.getAvailableSpaces().length);
    assertEquals(1, board.getAvailableSpaces()[0]);
    
    board.addMark( 5, Mark.PLAYER );
    assertEquals(7, board.getAvailableSpaces().length);
    assertEquals(6, board.getAvailableSpaces()[4]);
  }

  @Test
  public void boardAcceptsMark() {
    board.addMark( 0, Mark.COMPUTER );
    assertTrue( board.getAvailableSpaces().length == 8 );
    assertTrue( board.getMarkAtIndex( 0 ) == Mark.COMPUTER );
  }

  @Test
  public void boardErasesMark() {
    board.addMark( 0, Mark.COMPUTER );
    assertTrue( board.getMarkAtIndex( 0 ) == Mark.COMPUTER );
    board.eraseMark( 0 );
    assertFalse( board.getMarkAtIndex( 0 ) == Mark.COMPUTER );
    assertTrue( board.getMarkAtIndex( 0 ) == Mark.BLANK );
  }

  @Test
  public void boardResets() {
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 3, Mark.COMPUTER );
    board.addMark( 5, Mark.COMPUTER );
    board.addMark( 7, Mark.COMPUTER );
    board.reset();
    assertTrue( board.getAvailableSpaces().length == 9 );
  }

  @Test
  public void boardCanDetectWinningSolution() {
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 1, Mark.COMPUTER );
    assertFalse( board.hasWinningSolution() );

    board.addMark( 2, Mark.COMPUTER );
    assertTrue( board.hasWinningSolution() );
  }

  @Test
  public void boardCanFindWinningMark() {
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 1, Mark.COMPUTER );
    board.addMark( 2, Mark.COMPUTER );

    assertTrue( board.hasWinningSolution() );
    assertTrue( board.getWinningMark() == Mark.COMPUTER );
  }
}