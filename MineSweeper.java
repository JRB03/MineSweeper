import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
handles art and input
@author Jack Basinet
@version 1.31.20
*/
public class MineSweeper extends JComponent implements MouseListener, KeyListener
{
  /** gameboard */
  private GameBoard gameboard;

  /** flag mode */
  private boolean flag;
  int flags;

  private int difty;
  boolean over;
  boolean win;

  /**
  constructor
  @param difficulty the difficulty of the level
  */
  public MineSweeper(int difficulty, JFrame window)
  {
    difty = difficulty;
    over = false;
    flag = false;
    gameboard = new GameBoard(difty);
    flags = gameboard.flag(-1,-1);

    this.addMouseListener(this);
    window.addKeyListener(this);
  }

  /**
  restarts the game
  */
  public void restart()
  {
    over = false;
    win = false;
    flag = false;
    gameboard = new GameBoard(difty);
    flags = gameboard.flag(-1,-1);
  }

  /**
  handles user input from the mouse
  @param e the mouse event
  */
  public void mousePressed(MouseEvent e)
  {
    if(over || win) { restart(); }
    else if(flag)
    {
      flags = gameboard.flag((int)(e.getX() / RunGame.SQUARE_SIZE), (int)(e.getY() / RunGame.SQUARE_SIZE));
    }
    else
    {
      over =  gameboard.flip((int)(e.getX() / RunGame.SQUARE_SIZE), (int)(e.getY() / RunGame.SQUARE_SIZE));
    }
    if(gameboard.isWin() && !over) { win = true; }
    repaint();
  }
  public void mouseReleased(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}

  /**
  handles user input from the keyboard
  @param e the key event
  */
  public void keyPressed(KeyEvent e)
  {
     int keyCode = e.getKeyCode();

     if(keyCode == KeyEvent.VK_SPACE)
     {
       flag = true;

     }
  }
  public void keyReleased(KeyEvent e)
  {
    int keyCode = e.getKeyCode();

    if(keyCode == KeyEvent.VK_SPACE)
    {
      flag = false;
    }
  }
  public void keyTyped(KeyEvent e){}

  @Override
  protected void paintComponent(Graphics g)
  {
    gameboard.updateArt(g);
  }
}
