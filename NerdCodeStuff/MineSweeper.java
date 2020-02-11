import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

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
   /** the amount of flags left */
   int flags;

   /** if the game is over */
   boolean over;
   /** if the player has won */
   boolean win;

   /** if the player is selecting amode they wants to select next */
   private boolean e1;
   private boolean e2;
   private boolean m1;
   private boolean m2;
   private boolean h;
   private boolean c;

   /** length of board */
   private int length;
   /** width of board */
   private int width;
   private int bombs;

   JFrame wind;

   /**
   constructor
   @param difficulty the difficulty of the level
   */
   public MineSweeper(JFrame window, int length, int width, int bombs)
   {
      wind = window;

      over = false;
      flag = false;
      gameboard = new GameBoard(length, width, bombs);
      this.length = length;
      this.width = width;
      this.bombs = bombs;
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
      int lev = -1;
      if(e1) { lev = GameBoard.E1; }
      else if(e2) { lev = GameBoard.E2; }
      else if(m1) { lev = GameBoard.M1; }
      else if(m2) { lev = GameBoard.M2; }
      else if(h) { lev = GameBoard.H; }
      else if(c)
      {
        Scanner reader = new Scanner(System.in);
        boolean cont = true;
        while(cont)
        {
           System.out.print("Width: ");
           length = Integer.parseInt(reader.nextLine());
           System.out.print("Height: ");
           width = Integer.parseInt(reader.nextLine());
           System.out.print("Bombs: ");
           bombs = Integer.parseInt(reader.nextLine());
           if(!(length < 0 || length > 55 || width < 0 || width > 33 || bombs >= width * length)) {
             System.out.println();
             cont = false;
           } else { System.out.println(" too large an input..."); }
        }
      }
      if(lev == -1 || c) {}
      else {
         width = GameBoard.SIZE[lev];
         length = GameBoard.SIZE[lev + 1];
         bombs = GameBoard.BOMBS[lev/2]; }

      gameboard = new GameBoard(length, width, bombs);
      wind.setSize(length * RunGame.SQUARE_SIZE, width * RunGame.SQUARE_SIZE + 22);
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
         over = gameboard.flip((int)(e.getX() / RunGame.SQUARE_SIZE), (int)(e.getY() / RunGame.SQUARE_SIZE), false);
      }
      if(gameboard.isWin() && !over) {
         gameboard.gameOver(true);
         win = true; }
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

      if(keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_META)
      {
         flag = true;
      }
      if(keyCode == KeyEvent.VK_1) { e1 = true; }
      else if(keyCode == KeyEvent.VK_2) { e2 = true; }
      else if(keyCode == KeyEvent.VK_3) { m1 = true; }
      else if(keyCode == KeyEvent.VK_4) { m2 = true; }
      else if(keyCode == KeyEvent.VK_5) { h = true; }
      else if(keyCode == KeyEvent.VK_C) { c = true; }
   }
   public void keyReleased(KeyEvent e)
   {
      int keyCode = e.getKeyCode();

      if(keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_META)
      {
         flag = false;
      }
      if(keyCode == KeyEvent.VK_1) { e1 = false; }
      else if(keyCode == KeyEvent.VK_2) { e2 = false; }
      else if(keyCode == KeyEvent.VK_3) { m1 = false; }
      else if(keyCode == KeyEvent.VK_4) { m2 = false; }
      else if(keyCode == KeyEvent.VK_5) { h = false; }
      else if(keyCode == KeyEvent.VK_C) { c = false; }
   }
   public void keyTyped(KeyEvent e){}

   @Override
   protected void paintComponent(Graphics g)
   {
      gameboard.updateArt(g);
   }
}
