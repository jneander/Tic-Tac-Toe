package com.jneander.tictactoe.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jneander.tictactoe.game.Mark2;

public class GameView extends View {
  private float squareWidth;
  private float squareHeight;

  private int selectedRow;
  private int selectedCol;

  private final Rect selectedRect = new Rect();
  private final Rect targetRect = new Rect();

  Mark2 gameBoard2[] = new Mark2[9];

  public GameView( Context context ) {
    super( context );
    initialSetup();
  }

  public GameView( Context context, AttributeSet attrs ) {
    super( context, attrs );
    initialSetup();
  }

  public GameView( Context context, AttributeSet attrs, int defStyle ) {
    super( context, attrs, defStyle );
    initialSetup();
  }

  private void initialSetup() {
    setFocusable( true );
    setFocusableInTouchMode( true );

    clearBoard();
  }

  public void reset() {
    clearBoard();

    selectSquare( 1, 1 );
    invalidate();
  }

  private void clearBoard() {
    for ( int spaceIndex = 0; spaceIndex < gameBoard2.length; spaceIndex++ )
      gameBoard2[spaceIndex] = Mark2.BLANK;
  }

  private void getRect( int row, int col, Rect rect ) {
    rect.set( (int) (row * squareHeight) + 1, (int) (col * squareWidth) + 1,
        (int) (row * squareHeight + squareHeight), (int) (col * squareWidth + squareWidth) );
  }

  @Override
  protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
    squareWidth = w / 3f;
    squareHeight = h / 3f;
    getRect( selectedRow, selectedCol, selectedRect );

    super.onSizeChanged( w, h, oldw, oldh );
  }

  @Override
  protected void onDraw( Canvas canvas ) {
    drawBackground( canvas );
    drawGameBoardLines( canvas );
    drawSelectedSquare( canvas );
    drawMarks( canvas );
  }

  private void drawGameBoardLines( Canvas canvas ) {
    Paint blackPaint = new Paint();
    blackPaint.setColor( Color.BLACK );

    canvas.drawLine( 0, squareHeight, getWidth(), squareHeight, blackPaint );
    canvas.drawLine( 0, squareHeight * 2, getWidth(), squareHeight * 2, blackPaint );
    canvas.drawLine( squareWidth, 0, squareWidth, getHeight(), blackPaint );
    canvas.drawLine( squareWidth * 2, 0, squareWidth * 2, getHeight(), blackPaint );
  }

  private void drawBackground( Canvas canvas ) {
    Paint background = new Paint();
    background.setColor( Color.WHITE );
    canvas.drawRect( 0, 0, getWidth(), getHeight(), background );
  }

  private void drawMarks( Canvas canvas ) {
    Paint blackPaint = new Paint();
    blackPaint.setColor( Color.BLACK );
    blackPaint.setStyle( Style.STROKE );

    for ( int spaceIndex = 0; spaceIndex < gameBoard2.length; spaceIndex++ ) {
      int row = spaceIndex / 3;
      int col = spaceIndex % 3;

      if ( gameBoard2[spaceIndex] == Mark2.PLAYER ) {
        canvas.drawCircle( (row + 0.5f) * squareHeight, (col + 0.5f) * squareWidth, squareWidth * 0.25f, blackPaint );
      } else if ( gameBoard2[spaceIndex] == Mark2.COMPUTER ) {
        float top = (row + 0.25f) * squareHeight;
        float bottom = (row + 0.75f) * squareHeight;
        float left = (col + 0.25f) * squareWidth;
        float right = (col + 0.75f) * squareWidth;

        canvas.drawLine( top, left, bottom, right, blackPaint );
        canvas.drawLine( top, right, bottom, left, blackPaint );
      }
    }
  }

  private void drawSelectedSquare( Canvas canvas ) {
    Paint selected = new Paint();
    selected.setColor( Color.LTGRAY );
    canvas.drawRect( selectedRect, selected );
  }

  @Override
  protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
    super.onMeasure( widthMeasureSpec, widthMeasureSpec );
  }

  private void selectSquare( int row, int col ) {
    invalidate( selectedRect );

    selectedRow = row;
    selectedCol = col;
    getRect( selectedRow, selectedCol, selectedRect );

    invalidate( selectedRect );
  }

  private void redrawSquare( int row, int col ) {
    getRect( row, col, targetRect );
    invalidate( targetRect );
  }

  @Override
  public boolean onTouchEvent( MotionEvent event ) {
    if ( event.getAction() != MotionEvent.ACTION_DOWN )
      return super.onTouchEvent( event );

    int row = (int) (event.getX() / squareWidth);
    int col = (int) (event.getY() / squareHeight);

    selectSquare( row, col );

    return true;
  }

  public void updateMarkAtPosition( int spaceIndex, Mark2 mark ) {
    gameBoard2[spaceIndex] = mark;
    redrawSquare( spaceIndex / 3, spaceIndex % 3 );
  }

  public int getSelectedSpaceIndex() {
    return selectedRow * 3 + selectedCol;
  }
}