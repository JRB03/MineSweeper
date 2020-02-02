
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
    System.out.print("Welcome to MineSweeper! Would you like the rules? (y/n) ");
    Scanner yn = new Scanner(System.in);
    String rules = yn.nextLine();
    if(rules.equals("y") || rules.equals("yes") || rules.equals("Y"))
    {
      System.out.println("1. Click on a tile to reveal it\n2. Hold spacebar and click on a tile to flag it\n3. Each tiles value is how many bombs are adjacent to it (up to 8)\n4. Flag all the bombs and reveal all the other tiles to win!");
    }

    boolean input = false;
    int size = 0;
    int level = 1;
    while(size == 0)
    {
      System.out.print("Difficulty (0,1,2,3): ");
      Scanner reader = new Scanner(System.in);
      level = reader.nextInt();


      if(level == GameBoard.EASY) { size = GameBoard.SIZE[GameBoard.EASY] * SQUARE_SIZE; }
      else if(level == GameBoard.MEDIUM) { size = GameBoard.SIZE[GameBoard.MEDIUM] * SQUARE_SIZE; }
      else if(level == GameBoard.HARD) { size = GameBoard.SIZE[GameBoard.HARD] * SQUARE_SIZE; }
      else if(level == GameBoard.EXTREME) { size = GameBoard.SIZE[GameBoard.EXTREME] * SQUARE_SIZE; }
      else { System.out.println("invalid input..."); }
    }
    JFrame frame = new JFrame();
    JFrame score = new JFrame();

    frame.setSize(size, size + 22);
    frame.setTitle("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    score.setSize(180, 70);
    score.setTitle("Stats");

    MineSweeper game = new MineSweeper(level, frame);
    frame.add(game);

    ScoreBoard stats = new ScoreBoard(game);
    score.add(stats);

    frame.setVisible(true);
    score.setVisible(true);

  }
}
