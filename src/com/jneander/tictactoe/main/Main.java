package com.jneander.tictactoe.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jneander.tictactoe.R;
import com.jneander.tictactoe.game.Console;
import com.jneander.tictactoe.game.Game;
import com.jneander.tictactoe.game.Mark;
import com.jneander.tictactoe.ui.BoardView;

public class Main extends Activity implements Console {
  private final Game game = new Game( 3 );

  private BoardView gameView;
  private TextView messageView;
  private Button makeMarkButton;
  private Button newGameButton;

  @Override
  public void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.main );

    game.setConsole( this );

    gameView = (BoardView) findViewById( R.id.game_view );
    messageView = (TextView) findViewById( R.id.game_message );
    makeMarkButton = (Button) findViewById( R.id.button_make_mark );
    newGameButton = (Button) findViewById( R.id.button_new_game );

    gameView.setBoardSize( 3 );

    makeMarkButton.setOnClickListener( new OnClickListener() {
      @Override
      public void onClick( View v ) {
        int spaceIndex = gameView.getSelectedSpaceIndex();

        if ( !game.positionIsMarked( spaceIndex ) && !game.isGameOver() ) {
          gameView.updateMarkAtPosition( spaceIndex, Mark.PLAYER );
          game.makePlayerMarkAtPosition( spaceIndex );
        }
      }
    } );

    newGameButton.setOnClickListener( new OnClickListener() {
      @Override
      public void onClick( View v ) {
        messageView.setText( "" );
        gameView.reset();
        game.reset();
      }
    } );
  }

  @Override
  public void reportGameTied() {
    messageView.setText( getString( R.string.tie_message ) );
  }

  @Override
  public void reportGameLost() {
    messageView.setText( getString( R.string.lose_message ) );
  }

  @Override
  public void reportGameWon() {}

  @Override
  public void reportIllegalMove() {}

  @Override
  public void displayComputerMark( int position ) {
    gameView.updateMarkAtPosition( position, Mark.COMPUTER );
  }
}