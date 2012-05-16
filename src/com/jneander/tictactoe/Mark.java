package com.jneander.tictactoe;

public class Mark {
  public static enum MarkType {
    BLANK,
    PLAYER,
    COMPUTER
  }
  
  public final int row, col;
  public final MarkType markType;

  public Mark( int row, int col, MarkType markType ) {
    this.row = row;
    this.col = col;
    this.markType = markType;
  }
}