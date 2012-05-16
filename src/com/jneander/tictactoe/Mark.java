package com.jneander.tictactoe;

public class Mark {
  public static enum MarkType {
    BLANK,
    PLAYER,
    COMPUTER
  }
  
  public final int row, col;
  private MarkType markType;

  public Mark( int row, int col  ) {
    this.row = row;
    this.col = col;
    this.markType = MarkType.BLANK;
  }
  
  public MarkType getType() {
    return this.markType;
  }
  
  public void setToBlank() {
    this.markType = MarkType.BLANK;
  }
  
  public void setToPlayer() {
    this.markType = MarkType.PLAYER;
  }
  
  public void setToComputer() {
    this.markType = MarkType.COMPUTER;
  }
}