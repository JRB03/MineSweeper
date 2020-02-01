
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
Runs Minesweeper
@author Jack Basinet
@version 1.30.20
*/
public class RunGame
{
  public static final int SQUARE_SIZE = 25;

  public static void main(String[] args)
  {
    boolean input = false;
    int size = 0;
    int level = 1;
    while(size == 0)
    {
      System.out.print("Difficulty (0,1,2): ");
      Scanner reader = new Scanner(System.in);
      level = reader.nextInt();


      if(level == GameBoard.EASY) { size = GameBoard.SIZE[GameBoard.EASY] * SQUARE_SIZE; }
      else if(level == GameBoard.MEDIUM) { size = GameBoard.SIZE[GameBoard.MEDIUM] * SQUARE_SIZE; }
      else if(level == GameBoard.HARD) { size = GameBoard.SIZE[GameBoard.HARD] * SQUARE_SIZE; }
      else { System.out.println("invalid input..."); }
    }
    JFrame frame = new JFrame();

    frame.setSize(size, size + 22);
    frame.setTitle("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    MineSweeper game = new MineSweeper(level, frame);
    frame.add(game);

    frame.setVisible(true);

  }
}
