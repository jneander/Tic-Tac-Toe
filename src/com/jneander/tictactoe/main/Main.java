package com.jneander.tictactoe.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jneander.tictactoe.R;
import com.jneander.tictactoe.R.id;
import com.jneander.tictactoe.R.layout;
import com.jneander.tictactoe.R.string;
import com.jneander.tictactoe.game.Game;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.game.Mark.MarkType;
import com.jneander.tictactoe.ui.GameView;

public class Main extends Activity {
  private final Game game = new Game();

  private GameView gameView;
  private TextView messageView;
  private Button makeMarkButton;
  private Button newGameButton;

  @Override
  public void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );

    gameView = (GameView) findViewById( R.id.game_view );
    messageView = (TextView) findViewById( R.id.game_message );
    makeMarkButton = (Button) findViewById( R.id.button_make_mark );
    newGameButton = (Button) findViewById( R.id.button_new_game );

    gameView.setGameBoard( game.getGameBoard() );

    makeMarkButton.setOnClickListener( new OnClickListener() {
      @Override
      public void onClick( View v ) {
        int pos[] = gameView.getSelectedPos();

        if ( !game.positionIsMarked( pos[0], pos[1] ) ) {
          game.makePlayerMarkAtPosition( pos[0], pos[1] );
          gameView.updateMarkAtPosition( pos[0], pos[1] );

          if ( !game.isGameOver() ) {
            game.makeComputerMark();
            Mark lastMark = game.getLastMark();
            gameView.updateMarkAtPosition( lastMark.row, lastMark.col );
          }

          if ( game.isGameOver() ) {
            messageView.setText( (game.getWinner() == MarkType.COMPUTER) ?
                getString( R.string.lose_message ) : getString( R.string.tie_message ) );
          }
        }
      }
    } );

    newGameButton.setOnClickListener( new OnClickListener() {
      @Override
      public void onClick( View v ) {
        messageView.setText( "" );
        game.resetGame();
        gameView.setGameBoard( game.getGameBoard() );
      }
    } );
  }

  public void makeMark( int row, int col ) {
    if ( !game.positionIsMarked( row, col ) && !game.isGameOver() ) {
      game.makePlayerMarkAtPosition( row, col );
      Mark lastMark = game.getLastMark();
      gameView.updateMarkAtPosition( lastMark.row, lastMark.col );
    }
  }
}