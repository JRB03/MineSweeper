import java.lang.Math;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
creates the game board
@author Jack Basinet
@version 1.30.20
*/
public class GameBoard
{
   public static final int E1 = 0;
   public static final int E2 = 2;
   public static final int M1 = 4;
   public static final int M2 = 6;
   public static final int H = 8;
   public static final int CHOOSE = -1;

   /** bombs and sizes of board at each difficulty */
   public static final int[] BOMBS = {10, 10, 40, 39, 99};
   public static final int[] SIZE = {10, 10, 8, 8, 16, 16, 13, 15, 16, 30};

   /** the game board */
   private Square[][] board;
   private int length;
   private int width;

   /** the number of flags able to be used remaining */
   private int flags;

   /** if the game is over */
   private boolean over;

   /**
   sets up the game board
   @param length the length of the gameboard
   @param width the width of the gameboard
   @param bombs the number of bombs
   */
   public GameBoard(int length, int width, int bombs)
   {
      flags = bombs;
      over = false;
      this.length = length;
      this.width = width;

      board = new Square[length][width];
      for(int r = 0; r < board.length; r++)
      {
         for(int c = 0; c < board[0].length; c++)
         {
            board[r][c] = new Square(r, c);
         }
      }

      // does bombs
      for(int b = flags; b > 0; b--)
      {
         int row = (int)(Math.random() * length);
         int col = (int)(Math.random() * width);

         if (board[row][col].val != Square.BOMB) { board[row][col].val = Square.BOMB; }
         else { b++; }
      }

      // does vals
      for(Square[] row: board)
      {
         for(Square square: row)
         {
            int radar = 0;
            for(int a = -1; a < 2; a++)
            {
               if((square.row-1 >= 0 && square.col + a >=0 && square.col + a < width) && board[square.row-1][square.col + a].val == Square.BOMB) { radar++; }
            }
            for(int a = -1; a < 2; a++)
            {
               if((square.col + a >=0 && square.col + a < width) && board[square.row][square.col + a].val == Square.BOMB) { radar++; }
            }
            for(int a = -1; a < 2; a++)
            {
               if((square.row+1 < length && square.col + a >=0 && square.col + a < width) && board[square.row+1][square.col + a].val == Square.BOMB) { radar++; }
            }
            if(square.val != Square.BOMB) { square.val = radar; }
         }
      }
   }

   /**
   updates and loads the square art
   */
   public void updateArt(Graphics g)
   {
      for(Square[] row: board)
      {
         for(Square square: row)
         {
            BufferedImage img = square.getImage();
            g.drawImage(img, square.row * RunGame.SQUARE_SIZE, square.col * RunGame.SQUARE_SIZE, null);
         }
      }

   }

   /**
   checks to see if all non bombs have been shown
   @return boolean if player has won
   */
   public boolean isWin()
   {
      for(Square[] row: board)
      {
         for(Square square: row)
         {
            if(square.val == Square.BOMB) {}
            else if(!square.show) {
               return false; }
         }
      }
      return true;
   }

   /**
   flips all squares (end of game) and marks false flags
   */
   public void gameOver(boolean win)
   {
      for(Square[] row: board)
      {
         for(Square square: row)
         {
            if(!(square.val == Square.BOMB) && (square.flag))
            {
               square.val = -2;
            }
            if(win && square.val == Square.BOMB) { square.flag = true; }
            else { square.flag = false; }
            square.show = true;
         }
      }
   }

   /**
   shows a square
   @param r the row of the square
   @param c the column of the square
   @return boolean if bomb flipped
   */
   public boolean flip(int r, int c, boolean recurs)
   {
      Square square = board[r][c];
      if(square.flag) {
         return false; }
      if(square.show && !(recurs)) {
         int flagged = 0;
         for(int a = -1; a < 2; a++)
         {
            if((square.row-1 >= 0 && square.col + a >=0 && square.col + a < width) && board[square.row-1][square.col + a].flag) { flagged++; }
         }
         for(int a = -1; a < 2; a++)
         {
            if((square.col + a >= 0 && square.col + a < width) && board[square.row][square.col + a].flag) { flagged++; }
         }
         for(int a = -1; a < 2; a++)
         {
            if((square.row+1 < length && square.col + a >=0 && square.col + a < width) && board[square.row+1][square.col + a].flag) { flagged++; }
         }
         Square temp;
         if(square.val == flagged) {
            for(int a = -1; a < 2; a++)
            {
               if((square.row-1 >= 0 && square.col + a >=0 && square.col + a < width) && !board[square.row-1][square.col + a].flag) {
                  temp = board[square.row-1][square.col + a];
                  boolean m = flip(temp.row, temp.col, true);
                  if(!over) { over = m; } }
            }
            for(int a = -1; a < 2; a++)
            {
               if((square.col + a >=0 && square.col + a < width) && !board[square.row][square.col + a].flag) {
                  temp = board[square.row][square.col + a];
                  boolean m = flip(temp.row, temp.col, true);
                  if(!over) { over = m; } }
            }
            for(int a = -1; a < 2; a++)
            {
               if((square.row+1 < length && square.col + a >=0 && square.col + a < width) && !board[square.row+1][square.col + a].flag) {
                  temp = board[square.row+1][square.col + a];
                  boolean m = flip(temp.row, temp.col, true);
                  if(!over) { over = m; } }
            }
            return over;
         }
      }
      if(square.show) {
         return false; }

      square.show = true;

      if(square.val == 0) {
         for(int a = -1; a < 2; a++) {
            if((square.row-1 >= 0 && square.col + a >=0 && square.col + a < width)) { flip(square.row-1, square.col + a, true); }
         }
         for(int a = -1; a < 2; a++) {
            if((square.col + a >=0 && square.col + a < width)) { flip(square.row, square.col + a, true); }
         }
         for(int a = -1; a < 2; a++) {
            if((square.row+1 < length && square.col + a >=0 && square.col + a < width)) { flip(square.row+1, square.col + a, true); }
         }
         for(int a = -1; a < 2; a++) {  }
      } else if(square.val == -1) {
         this.gameOver(false);
         return true;
      }
      return false;
   }

   /**
   shows a square
   @param r the row of the square
   @param c the column of the square
   @return int the number of flags left
   */
   public int flag(int r, int c)
   {
      if(r == -1 && c == -1) {}
      else if(!board[r][c].show)
      {
         if(board[r][c].flag)
         {
            board[r][c].flag = false;
            flags++;
         }
         else if(flags > 0)
         {
            board[r][c].flag = true;
            flags--;
         }
      }
      return flags;
   }

   /**
   holds the information for individual squares
   @author Jack Basinet
   @version 1.30.20
   */
   private class Square
   {
      public static final int BOMB = -1;

      /** the row and column of the square */
      int row;
      int col;

      /** the number of bombs around the square (-1 if bomb) */
      int val;

      /** whether or not the square is showing */
      boolean show;

      /** whether or not the square is flagged */
      boolean flag;

      /** img vars for squares */
      private String imgFileName;
      private BufferedImage image;

      /**
      constructor
      @param row the row of the square
      @param col the column of the square
      */
      public Square(int row, int col)
      {
         this.row = row;
         this.col = col;
         this.val = 0;
         this.show = false;
         this.flag = false;
      }

      /**
      gets the image for the square depending on value
      @return BufferedImage the image of the square
      */
      public BufferedImage getImage()
      {
         if(flag) { imgFileName = "Art/flag.png"; }
         else if(!show) { imgFileName = "Art/hide.png"; }
         else { imgFileName = "Art/" + val + ".png"; }

         BufferedImage squareImg = null;
         try
         {
            squareImg = ImageIO.read(new File(imgFileName));
         } catch (IOException e) {}

         image = new BufferedImage(RunGame.SQUARE_SIZE, RunGame.SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);

         Graphics2D imgG = image.createGraphics();
         imgG.drawImage(squareImg, 0, 0, null);

         return image;
      }
   }
}
