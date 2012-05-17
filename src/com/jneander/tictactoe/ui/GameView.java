package com.jneander.tictactoe.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class GameView extends View {
  private float width;
  private float height;
  private int selectedRow;
  private int selectedCol;
  private final Rect selRect = new Rect();

  public GameView( Context context ) {
    super( context );
    initialSetup();
  }

  public GameView( Context context, AttributeSet attrs ) {
    super( context, attrs );
    initialSetup();
  }

  private void initialSetup() {
    setFocusable( true );
    setFocusableInTouchMode( true );
  }

  private void getRect( int row, int col, Rect rect ) {
    rect.set( (int) (row * width), (int) (col * height),
        (int) (row * width + width), (int) (col * height + height) );
  }

  @Override
  protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
    width = w / 3f;
    height = h / 3f;
    getRect( selectedRow, selectedCol, selRect );
    super.onSizeChanged( w, h, oldw, oldh );
  }

  @Override
  protected void onDraw( Canvas canvas ) {
    Paint background = new Paint();
    background.setColor( Color.WHITE );
    canvas.drawRect( 0, 0, getWidth(), getHeight(), background );

    Paint blackPaint = new Paint();
    blackPaint.setColor( Color.BLACK );

    canvas.drawLine( 0, height, getWidth(), height, blackPaint );
    canvas.drawLine( 0, height * 2, getWidth(), height * 2, blackPaint );
    canvas.drawLine( width, 0, width, getHeight(), blackPaint );
    canvas.drawLine( width * 2, 0, width * 2, getHeight(), blackPaint );
  }

  @Override
  protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
    super.onMeasure( widthMeasureSpec, widthMeasureSpec );
  }

  @Override
  public boolean onKeyDown( int keyCode, KeyEvent event ) {
    switch ( keyCode ) {
    case KeyEvent.KEYCODE_DPAD_UP:
      select( selectedRow, selectedCol - 1 );
      break;
    case KeyEvent.KEYCODE_DPAD_DOWN:
      select( selectedRow, selectedCol + 1 );
      break;
    case KeyEvent.KEYCODE_DPAD_LEFT:
      select( selectedRow - 1, selectedCol );
      break;
    case KeyEvent.KEYCODE_DPAD_RIGHT:
      select( selectedRow + 1, selectedCol );
      break;
    default:
      return super.onKeyDown( keyCode, event );
    }
    return true;
  }

  private void select( int row, int col ) {
    invalidate( selRect );
    selectedRow = Math.min( Math.max( row, 0 ), 2 );
    selectedCol = Math.min( Math.max( col, 0 ), 2 );
  }
}