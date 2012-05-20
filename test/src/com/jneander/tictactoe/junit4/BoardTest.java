package com.jneander.tictactoe.junit4;

import static org.junit.Assert.assertTrue;

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
}