package com.jneander.tictactoe.junit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark2;

public class BoardTest {
  private Board board;

  @Before
  public void setUp() {
    board = new Board();
  }

  @Test
  public void boardAcceptsMark() {
    board.addMark( 0, Mark2.COMPUTER );
    assertTrue( board.getAvailableSpaces().length == 8 );
    assertTrue( board.getMarkAtIndex( 0 ) == Mark2.COMPUTER );
  }

  @Test
  public void boardErasesMark() {
    board.addMark( 0, Mark2.COMPUTER );
    assertTrue( board.getMarkAtIndex( 0 ) == Mark2.COMPUTER );
    board.eraseMark( 0 );
    assertFalse( board.getMarkAtIndex( 0 ) == Mark2.COMPUTER );
    assertTrue( board.getMarkAtIndex( 0 ) == Mark2.BLANK );
  }

  @Test
  public void boardResets() {
    board.addMark( 0, Mark2.COMPUTER );
    board.addMark( 3, Mark2.COMPUTER );
    board.addMark( 5, Mark2.COMPUTER );
    board.addMark( 7, Mark2.COMPUTER );
    board.reset();
    assertTrue( board.getAvailableSpaces().length == 9 );
  }

  @Test
  public void boardCanDetectWinningSolution() {
    board.addMark( 0, Mark2.COMPUTER );
    board.addMark( 1, Mark2.COMPUTER );
    assertFalse( board.hasWinningSolution() );

    board.addMark( 2, Mark2.COMPUTER );
    assertTrue( board.hasWinningSolution() );
  }

  @Test
  public void boardCanFindWinningMark() {
    board.addMark( 0, Mark2.COMPUTER );
    board.addMark( 1, Mark2.COMPUTER );
    board.addMark( 2, Mark2.COMPUTER );

    assertTrue( board.hasWinningSolution() );
    assertTrue( board.getWinningMark() == Mark2.COMPUTER );
  }
}