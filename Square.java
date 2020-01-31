/**
holds the information for individual squares
@author Jack Basinet
@version 1.30.20
*/
public class Square
{
  public static final int BOMB = -1;

  /** the row and column of the square */
  private int row;
  private int col;

  /** the number of bombs around the square (-1 if bomb) */
  private int val;

  /** whether or not the square is showing */
  private boolean show;
}
