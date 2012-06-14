package test.jneander.tictactoe.junit4;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Computer;
import com.jneander.tictactoe.game.ImpossibleComputer;
import com.jneander.tictactoe.game.Mark;

public class ImpossibleComputerTest {
  private Board board;
  private Computer computer;

  @Before
  public void setUp() {
    board = new Board( 3 );
    computer = new ImpossibleComputer( board );
  }

  @Test
  public void computerNeverLosesWhenGoingFirst() {
    board.addMark( computer.getNextMarkPosition(), Mark.COMPUTER );
    tryPlayerMoves( board, computer );
  }

  @Test
  public void computerNeverLosesWhenGoingSecond() {
    tryPlayerMoves( board, computer );
  }

  private void tryPlayerMoves( Board board, Computer computer ) {
    int[] availableSpaces = board.getAvailableSpaces();

    for ( int spaceIndex = 0; spaceIndex < availableSpaces.length; spaceIndex++ ) {
      board.addMark( availableSpaces[spaceIndex], Mark.PLAYER );
      assertFalse( board.hasWinningSolution() || board.getWinningMark() == Mark.PLAYER );

      if ( board.getAvailableSpaces().length > 0 ) {
        int nextComputerMarkPosition = computer.getNextMarkPosition();
        board.addMark( nextComputerMarkPosition, Mark.COMPUTER );

        if ( !board.hasWinningSolution() )
          tryPlayerMoves( board, computer );

        board.eraseMark( nextComputerMarkPosition );
      }

      assertFalse( board.getWinningMark() == Mark.PLAYER );
      board.eraseMark( availableSpaces[spaceIndex] );
    }
  }
}