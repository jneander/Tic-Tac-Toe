package com.jneander.tictactoe.junit4;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
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

    int bestIndex = getBestIndex( board, Mark2.COMPUTER );
    assertTrue( bestIndex == 6 );
  }

  @Ignore( "Only run if time permitting; takes about 5 minutes" )
  @Test
  public void computerNeverLosesWhenGoingFirst() {
    int markIndices[] = new int[4];

    for ( markIndices[0] = 0; markIndices[0] < 9; markIndices[0]++ ) {
      for ( markIndices[1] = 0; markIndices[1] < 9; markIndices[1]++ ) {
        for ( markIndices[2] = 0; markIndices[2] < 9; markIndices[2]++ ) {
          for ( markIndices[3] = 0; markIndices[3] < 9; markIndices[3]++ ) {
            if ( integersAreUnique( markIndices ) ) {
              board = new Board();
              boolean terminate = false;

              for ( int turn = 0; turn < markIndices.length && !terminate; turn++ ) {
                board.addMark( getBestIndex( board, Mark2.COMPUTER ), Mark2.COMPUTER );

                if ( !board.hasWinningSolution() ) {
                  if ( board.getMarkAtIndex( markIndices[turn] ) != Mark2.BLANK )
                    terminate = true;
                  else
                    board.addMark( markIndices[turn], Mark2.PLAYER );
                }

                if ( board.hasWinningSolution() )
                  terminate = true;
              }

              if ( board.hasWinningSolution() )
                if ( board.getWinningMark() == Mark2.PLAYER )
                  fail( "PLAYER CANNOT WIN!" );
            }
          }
        }
      }
    }
  }

  private boolean integersAreUnique( int integers[] ) {
    boolean areUnique = true;

    for ( int first = 0; first < integers.length - 1; first++ )
      for ( int second = first + 1; second < integers.length; second++ )
        areUnique &= first != second;

    return areUnique;
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