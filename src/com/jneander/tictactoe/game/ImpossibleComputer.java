package com.jneander.tictactoe.game;

import com.jneander.tictactoe.util.Minimax;

public class ImpossibleComputer implements Computer {
  private final Board board;

  public ImpossibleComputer( Board board ) {
    this.board = board;
  }

  public int getNextMarkPosition() {
    int score = 0;
    int[] availableSpaces = board.getAvailableSpaces();
    int bestIndex = 0;

    if ( board.hasWinningSolution() )
      score = ((board.getWinningMark() == Mark.COMPUTER) ? 1 : -1);
    else if ( availableSpaces.length != 0 ) {
      for ( int availableIndex = 0; availableIndex < availableSpaces.length && score != 1; availableIndex++ ) {
        int nextScore = getChildBoardScore( availableSpaces[availableIndex] );

        if ( nextScore > score || availableIndex == 0 ) {
          score = nextScore;
          bestIndex = availableSpaces[availableIndex];
        }
      }
    }

    return bestIndex;
  }

  private int getChildBoardScore( int spaceIndex ) {
    board.addMark( spaceIndex, Mark.COMPUTER );
    int childScore = Minimax.minimax( board, Mark.PLAYER );
    board.eraseMark( spaceIndex );

    return childScore;
  }
}