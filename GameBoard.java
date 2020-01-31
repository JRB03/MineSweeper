import java.lang.Math;

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

  /** the game board */
  private Square[][] board;

  /** bombs and sizes of board at each difficulty */
  private int[] bombs = {10, 40, 99};
  private int[] size = {9, 16, 22};

  /**
  sets up the game board
  @param level the difficulty of the level (easy,medium,hard)
  */
  public GameBoard(int level)
  {
    int size = this.size[level];
    board = new Square[size][size];

    for(int r = 0; r < size; r++)
    {
      for(int c = 0; c < size; c++)
      {
        Square temp = new Square();
        temp.row = r;
        temp.col = c;
        temp.val = 0;
        temp.show = false;

        board[r][c] = temp;
      }
    }

    for(int bombs = this.bombs[level]; bombs > 0; bombs--)
    {
      int row = 0;
      int col = 0;
      while(board[row][col].val != Square.BOMB)
      {
        row = (int)(Math.random() * this.size[level]);
        col = (int)(Math.random() * this.size[level]);

        board[row][col].val = Square.BOMB;
      }
    }

    for(Square[] row: board)
    {
      for(Square square: row)
      {
        int radar = 0;
        for(int a = -1; a < 2; a++)
        {
          if(board[square.row-1][square.col + a].val == Square.BOMB) { radar++; }
        }
        for(int a = -1; a < 2; a++)
        {
          if(board[square.row][square.col + a].val == Square.BOMB) { radar++; }
        }
        for(int a = -1; a < 2; a++)
        {
          if(board[square.row+1][square.col + a].val == Square.BOMB) { radar++; }
        }
        square.val = radar;
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
  shows a square
  @param r the row of the square
  @param c the column of the square
  @return int the value of the square
  */
  public int flip(int r, int c)
  {
    board[r][c].show = true;
    return board[r][c].val;
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
  }
}
