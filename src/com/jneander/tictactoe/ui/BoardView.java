package com.jneander.tictactoe.ui;

import com.jneander.tictactoe.game.Mark;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {
  private float squareWidth;
  private float squareHeight;

  private int selectedRow;
  private int selectedCol;

  private final Rect selectedRect = new Rect();
  private final Rect targetRect = new Rect();

  private int boardSize = 3;
  private Mark gameBoard[] = new Mark[boardSize];

  public BoardView( Context context ) {
    super( context );
    setViewBehavior();
  }

  public BoardView( Context context, AttributeSet attrs ) {
    super( context, attrs );
    setViewBehavior();
  }

  public BoardView( Context context, AttributeSet attrs, int defStyle ) {
    super( context, attrs, defStyle );
    setViewBehavior();
  }

  private void setViewBehavior() {
    setFocusable( true );
    setFocusableInTouchMode( true );

    setBoardSize( boardSize );
  }

  public void setBoardSize( int size ) {
    boardSize = (size > 0) ? size : boardSize;
    gameBoard = new Mark[boardSize * boardSize];
    reset();
  }

  public void reset() {
    clearBoard();

    int firstHighlightIndex = (boardSize % 2 == 0) ? 0 : (boardSize / 2);
    selectSquare( firstHighlightIndex, firstHighlightIndex );
    invalidate();
  }

  private void clearBoard() {
    for ( int spaceIndex = 0; spaceIndex < gameBoard.length; spaceIndex++ )
      gameBoard[spaceIndex] = Mark.BLANK;
  }

  private void getRect( int row, int col, Rect rect ) {
    rect.set( (int) (row * squareHeight) + 1, (int) (col * squareWidth) + 1,
        (int) (row * squareHeight + squareHeight), (int) (col * squareWidth + squareWidth) );
  }

  @Override
  protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
    squareWidth = w / (float) boardSize;
    squareHeight = h / (float) boardSize;
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

    for ( int rowIndex = 1; rowIndex < boardSize; rowIndex++ )
      canvas.drawLine( 0, squareHeight * rowIndex, getWidth(), squareHeight * rowIndex, blackPaint );
    for ( int colIndex = 1; colIndex < boardSize; colIndex++ )
      canvas.drawLine( squareWidth * colIndex, 0, squareWidth * colIndex, getHeight(), blackPaint );
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

    for ( int spaceIndex = 0; spaceIndex < gameBoard.length; spaceIndex++ ) {
      int row = spaceIndex / boardSize;
      int col = spaceIndex % boardSize;

      if ( gameBoard[spaceIndex] == Mark.PLAYER ) {
        canvas.drawCircle( (row + 0.5f) * squareHeight, (col + 0.5f) * squareWidth, squareWidth * 0.25f, blackPaint );
      } else if ( gameBoard[spaceIndex] == Mark.COMPUTER ) {
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

  public void updateMarkAtPosition( int spaceIndex, Mark mark ) {
    gameBoard[spaceIndex] = mark;
    redrawSquare( spaceIndex / boardSize, spaceIndex % boardSize );
  }

  public int getSelectedSpaceIndex() {
    return selectedRow * boardSize + selectedCol;
  }
}