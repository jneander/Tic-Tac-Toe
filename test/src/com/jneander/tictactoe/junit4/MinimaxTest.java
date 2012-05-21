package com.jneander.tictactoe.junit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark2;
import com.jneander.tictactoe.util.Minimax;

public class MinimaxTest {
  private Board board;

  @Before
  public void setUp() {
    board = new Board();
  }

  @Test
  public void minimaxFindsWinningMove() {
    board.addMark( 0, Mark2.COMPUTER );
    board.addMark( 2, Mark2.PLAYER );
    board.addMark( 3, Mark2.COMPUTER );
    board.addMark( 5, Mark2.PLAYER );

    int bestIndex = getBestIndex(board,Mark2.COMPUTER);
    assertTrue( bestIndex == 6 );
  }
  
  private int getBestIndex( Board board, Mark2 currentMark ) {
    int score = ((currentMark == Mark2.COMPUTER) ? -100 : 100);
    int[] availableSpaces = board.getAvailableSpaces();
    int bestIndex = availableSpaces[0];

    if ( board.hasWinningSolution() )
      score = ((board.getWinningMark() == Mark2.COMPUTER) ? 1 : -1);
    else if ( availableSpaces.length != 0 ) {
      Mark2 nextMark = (currentMark == Mark2.COMPUTER) ? Mark2.PLAYER : Mark2.COMPUTER;

      for ( int availableIndex = 0; availableIndex < availableSpaces.length; availableIndex++ ) {
        board.addMark( availableSpaces[availableIndex], currentMark );
        int nextScore = Minimax.minimax( board, nextMark );
        board.eraseMark( availableSpaces[availableIndex] );

        if ( currentMark == Mark2.COMPUTER && nextScore > score
            || currentMark == Mark2.PLAYER && nextScore < score ) {
          score = nextScore;
          bestIndex = availableSpaces[availableIndex];
        }
      }
    }

    return bestIndex;
  }
}