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
  public static final int EASY = 0;
  public static final int MEDIUM = 1;
  public static final int HARD = 2;

  /** bombs and sizes of board at each difficulty */
  public static final int[] BOMBS = {13, 44, 105};
  public static final int[] SIZE = {9, 18, 25};

  /** the game board */
  private Square[][] board;

  /**
  sets up the game board
  @param level the difficulty of the level (easy,medium,hard)
  */
  public GameBoard(int level)
  {
    int size = SIZE[level];
    board = new Square[size][size];

    for(int r = 0; r < board.length; r++)
    {
      for(int c = 0; c < board[0].length; c++)
      {
        board[r][c] = new Square(r, c, 0);
      }
    }

    for(int b = BOMBS[level]; b > 0; b--)
    {
      int row = (int)(Math.random() * size);
      int col = (int)(Math.random() * size);

      if (board[row][col].val != Square.BOMB) { board[row][col].val = Square.BOMB; }
      else { b++; }
    }

    for(Square[] row: board)
    {
      for(Square square: row)
      {
        int radar = 0;
        for(int a = -1; a < 2; a++)
        {
          if((square.row-1 >= 0 && square.col + a >=0 && square.col + a < size) && board[square.row-1][square.col + a].val == Square.BOMB) { radar++; }
        }
        for(int a = -1; a < 2; a++)
        {
          if((square.col + a >=0 && square.col + a < size) && board[square.row][square.col + a].val == Square.BOMB) { radar++; }
        }
        for(int a = -1; a < 2; a++)
        {
          if((square.row+1 < size && square.col + a >=0 && square.col + a < size) && board[square.row+1][square.col + a].val == Square.BOMB) { radar++; }
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
        if((square.val != Square.BOMB) && !(square.show))
        {
          return false;
        }
      }
    }
    return true;
  }

  /**
  flips all squares (end of game)
  @return boolean if player has won
  */
  public void gameOver()
  {
    for(Square[] row: board)
    {
      for(Square square: row)
      {
        if(!(square.val == Square.BOMB) && (square.flag))
        {
          square.val = -2;
        }
        square.flag = false;
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
  public boolean flip(int r, int c)
  {
    Square square = board[r][c];
    if(square.show == true || square.flag) { return false; }

    square.show = true;
    if(square.val == 0)
    {
      int size = board.length;
      for(int a = -1; a < 2; a++)
      {
        if((square.row-1 >= 0 && square.col + a >=0 && square.col + a < size)) { flip(square.row-1, square.col + a); }
      }
      for(int a = -1; a < 2; a++)
      {
        if((square.col + a >=0 && square.col + a < size))
        { flip(square.row, square.col + a); }
      }
      for(int a = -1; a < 2; a++)
      {
        if((square.row+1 < size && square.col + a >=0 && square.col + a < size)) { flip(square.row+1, square.col + a); }
      }
      for(int a = -1; a < 2; a++) {  }
    }
    else if(square.val == -1)
    {
      this.gameOver();
      return true;
    }
    return false;
  }

  /**
  shows a square
  @param r the row of the square
  @param c the column of the square
  @return int the value of the square
  */
  public void flag(int r, int c)
  {
    if(!board[r][c].show)
    {
      board[r][c].flag = !board[r][c].flag;
    }
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

    public Square(int row, int col, int val)
    {
      this.row = row;
      this.col = col;
      this.val = val;
      this.show = false;
      this.flag = false;
    }

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
