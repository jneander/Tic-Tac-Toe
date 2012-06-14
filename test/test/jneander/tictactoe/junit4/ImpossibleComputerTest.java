package test.jneander.tictactoe.junit4;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.jneander.tictactoe.game.Board;
import com.jneander.tictactoe.game.Computer;
import com.jneander.tictactoe.game.ImpossibleComputer;
import com.jneander.tictactoe.game.Mark;

public class ImpossibleComputerTest {
  private Board board3x3;
  private Computer computer3x3;

  @Before
  public void setUp() {
    board3x3 = new Board( 3 );
    computer3x3 = new ImpossibleComputer( board3x3 );
  }

  @Test
  public void computerNeverLosesWhenGoingFirst() {
    board3x3.addMark( computer3x3.getNextMarkPosition(), Mark.COMPUTER );
    tryPlayerMoves( board3x3, computer3x3 );
  }

  @Test
  public void computerNeverLosesWhenGoingSecond() {
    tryPlayerMoves( board3x3, computer3x3 );
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