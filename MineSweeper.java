import javax.swing.*;
import java.awt.*;

/**
draws the board
@author Jack Basinet
@version 1.31.20
*/
public class MineSweeper extends JComponent
{
  /** gameboard */
  private GameBoard gameboard;

  /**
  constructor
  @param difficulty the difficulty of the level
  */
  public MineSweeper(int difficulty)
  {
    gameboard = new GameBoard(difficulty);
    gameboard.loadSquareArt();
  }

  /**

  @param
  @return
  */
  public void updateGame()
  {
    gameboard.loadSquareArt();
  }

}
