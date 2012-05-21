package com.jneander.tictactoe.junit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.util.Minimax;

public class MinimaxTest {
  private Board board;

  @Before
  public void setUp() {
    board = new Board();
  }

  @Ignore
  @Test
  public void minimaxFindsWinningMove() {
    board.addMark( 0, Mark.COMPUTER );
    board.addMark( 2, Mark.PLAYER );
    board.addMark( 3, Mark.COMPUTER );
    board.addMark( 5, Mark.PLAYER );

    int bestIndex = getBestIndex( board, Mark.COMPUTER );
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
                board.addMark( getBestIndex( board, Mark.COMPUTER ), Mark.COMPUTER );

                if ( !board.hasWinningSolution() ) {
                  if ( board.getMarkAtIndex( markIndices[turn] ) != Mark.BLANK )
                    terminate = true;
                  else
                    board.addMark( markIndices[turn], Mark.PLAYER );
                }

                if ( board.hasWinningSolution() )
                  terminate = true;
              }

              if ( board.hasWinningSolution() )
                assertFalse( board.getWinningMark() == Mark.PLAYER );
            }
          }
        }
      }
    }
  }

  @Ignore( "Only run if time permitting; takes about 5 minutes" )
  @Test
  public void computerNeverLosesWhenGoingSecond() {
    int markIndices[] = new int[5];

    for ( markIndices[0] = 0; markIndices[0] < 9; markIndices[0]++ ) {
      for ( markIndices[1] = 0; markIndices[1] < 9; markIndices[1]++ ) {
        for ( markIndices[2] = 0; markIndices[2] < 9; markIndices[2]++ ) {
          for ( markIndices[3] = 0; markIndices[3] < 9; markIndices[3]++ ) {
            for ( markIndices[4] = 0; markIndices[4] < 9; markIndices[4]++ ) {
              if ( integersAreUnique( markIndices ) ) {
                board = new Board();
                boolean terminate = false;

                for ( int turn = 0; turn < markIndices.length && !terminate; turn++ ) {
                  if ( board.getMarkAtIndex( markIndices[turn] ) != Mark.BLANK )
                    terminate = true;
                  else
                    board.addMark( markIndices[turn], Mark.PLAYER );

                  if ( !board.hasWinningSolution() && turn != 4 && !terminate )
                    board.addMark( getBestIndex( board, Mark.COMPUTER ),
                        Mark.COMPUTER );

                  if ( !terminate && board.hasWinningSolution() )
                    terminate = true;
                }

                if ( board.hasWinningSolution() ) {
                  assertFalse( board.getWinningMark() == Mark.PLAYER );
                }
              }
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
        areUnique &= integers[first] != integers[second];

    return areUnique;
  }

  private int getBestIndex( Board board, Mark currentMark ) {
    int score = 0;
    int[] availableSpaces = board.getAvailableSpaces();
    int bestIndex = availableSpaces[0];

    if ( board.hasWinningSolution() )
      score = ((board.getWinningMark() == Mark.COMPUTER) ? 1 : -1);
    else if ( availableSpaces.length != 0 ) {
      Mark nextMark = (currentMark == Mark.COMPUTER) ? Mark.PLAYER : Mark.COMPUTER;

      for ( int availableIndex = 0; availableIndex < availableSpaces.length; availableIndex++ ) {
        board.addMark( availableSpaces[availableIndex], currentMark );
        int nextScore = Minimax.minimax( board, nextMark );
        board.eraseMark( availableSpaces[availableIndex] );

        if ( currentMark == Mark.COMPUTER && nextScore > score
            || currentMark == Mark.PLAYER && nextScore < score
            || availableIndex == 0 ) {
          score = nextScore;
          bestIndex = availableSpaces[availableIndex];
        }
      }
    }

    return bestIndex;
  }
}