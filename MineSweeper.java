import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
draws the board
@author Jack Basinet
@version 1.31.20
*/
public class MineSweeper extends JComponent
{
  /**
  constructor
  @param difficulty the difficulty of the level
  */
  public MineSweeper(int difficulty)
  {
    GameBoard gameboard = new GameBoard(difficulty);
  }

  @Override
  public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;

      super.paintComponent(g);

      Rectangle2D.Double avatar = new Rectangle2D.Double(0,0,20,20);
      g2.fill(avatar);
   }



}
