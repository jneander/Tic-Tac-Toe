package test.jneander.tictactoe.junit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;

public class BoardTest {
  private Board board3x3;
  private Board board4x4;

  @Before
  public void setUp() {
    board3x3 = new Board( 3 );
    board4x4 = new Board( 4 );
  }

  @Test
  public void canGetAvailableSpaces() {
    assertEquals( 9, board3x3.getAvailableSpaces().length );

    board3x3.addMark( 0, Mark.PLAYER );
    assertEquals( 8, board3x3.getAvailableSpaces().length );
    assertEquals( 1, board3x3.getAvailableSpaces()[0] );

    board3x3.addMark( 5, Mark.PLAYER );
    assertEquals( 7, board3x3.getAvailableSpaces().length );
    assertEquals( 6, board3x3.getAvailableSpaces()[4] );
  }

  @Test
  public void boardAcceptsMark() {
    board3x3.addMark( 0, Mark.COMPUTER );
    assertTrue( board3x3.getAvailableSpaces().length == 8 );
    assertTrue( board3x3.getMarkAtIndex( 0 ) == Mark.COMPUTER );
  }

  @Test
  public void boardErasesMark() {
    board3x3.addMark( 0, Mark.COMPUTER );
    assertTrue( board3x3.getMarkAtIndex( 0 ) == Mark.COMPUTER );
    board3x3.eraseMark( 0 );
    assertFalse( board3x3.getMarkAtIndex( 0 ) == Mark.COMPUTER );
    assertTrue( board3x3.getMarkAtIndex( 0 ) == Mark.BLANK );
  }

  @Test
  public void boardResets() {
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 3, Mark.COMPUTER );
    board3x3.addMark( 5, Mark.COMPUTER );
    board3x3.addMark( 7, Mark.COMPUTER );
    board3x3.reset();
    assertTrue( board3x3.getAvailableSpaces().length == 9 );
  }

  @Test
  public void boardCanDetectWinningSolution3x3() {
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 1, Mark.COMPUTER );
    assertFalse( board3x3.hasWinningSolution() );

    board3x3.addMark( 2, Mark.COMPUTER );
    assertTrue( board3x3.hasWinningSolution() );
  }

  @Test
  public void boardCanDetectWinningSolution4x4() {
    board4x4.addMark( 0, Mark.COMPUTER );
    board4x4.addMark( 1, Mark.COMPUTER );
    board4x4.addMark( 2, Mark.COMPUTER );
    assertFalse( board4x4.hasWinningSolution() );

    board4x4.addMark( 3, Mark.COMPUTER );
    assertTrue( board4x4.hasWinningSolution() );
  }

  @Test
  public void boardCanFindWinningMark3x3() {
    board3x3.addMark( 0, Mark.COMPUTER );
    board3x3.addMark( 1, Mark.COMPUTER );
    board3x3.addMark( 2, Mark.COMPUTER );

    assertTrue( board3x3.hasWinningSolution() );
    assertTrue( board3x3.getWinningMark() == Mark.COMPUTER );
  }

  @Test
  public void boardCanFindWinningMark4x4() {
    board4x4.addMark( 0, Mark.COMPUTER );
    board4x4.addMark( 1, Mark.COMPUTER );
    board4x4.addMark( 2, Mark.COMPUTER );
    board4x4.addMark( 3, Mark.COMPUTER );

    assertTrue( board4x4.hasWinningSolution() );
    assertTrue( board4x4.getWinningMark() == Mark.COMPUTER );
  }
}