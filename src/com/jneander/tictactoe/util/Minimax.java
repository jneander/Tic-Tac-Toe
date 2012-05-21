package com.jneander.tictactoe.util;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Mark;

public class Minimax {
  public static int minimax( Board board, Mark currentMark ) {
    int score = 0;
    int[] availableSpaces = board.getAvailableSpaces();

    if ( board.hasWinningSolution() )
      score = (board.getWinningMark() == Mark.COMPUTER) ? 1 : -1;
    else if ( availableSpaces.length != 0 ) {
      Mark nextMark = (currentMark == Mark.COMPUTER) ? Mark.PLAYER : Mark.COMPUTER;
      int bestValue = (currentMark == Mark.COMPUTER) ? 1 : -1;

      for ( int availableIndex = 0; availableIndex < availableSpaces.length && score != bestValue; availableIndex++ ) {
        board.addMark( availableSpaces[availableIndex], currentMark );
        int nextScore = minimax( board, nextMark );
        board.eraseMark( availableSpaces[availableIndex] );

        if ( currentMark == Mark.COMPUTER && nextScore > score
            || currentMark == Mark.PLAYER && nextScore < score
            || availableIndex == 0 )
          score = nextScore;
      }
    }

    return score;
  }
}