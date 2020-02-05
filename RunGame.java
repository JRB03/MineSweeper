
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
  public static final int SCORE_X = 180;
  public static final int SCORE_Y = 70;

  public static void main(String[] args)
  {
    System.out.print("Welcome to MineSweeper!\nWould you like the rules? (y/n) ");
    Scanner yn = new Scanner(System.in);
    String rules = yn.nextLine();
    if(rules.equals("y") || rules.equals("yes") || rules.equals("Y"))
    {
      System.out.println("1. Click on a tile to reveal it\n2. Hold spacebar and click on a tile to flag it\n3. Each tiles value is how many bombs are adjacent to it (up to 8)\n4. Click on a shown tile whose flag value has been met to reveal the other tiles around it\n5. Flag all the bombs to win!\n6. Once you win, hold the number level you'd like to play next (none if you want to do the same) and click the screen anywhere to start again");
    }

    boolean input = false;
    int length = 0;
    int width = 0;
    int bombs = 0;
    int level = 1;
    while(length == 0)
    {
      System.out.print("Difficulty (0,1,2,3,c): ");
      Scanner reader = new Scanner(System.in);
      String temp = reader.nextLine();
      if(temp.equals("c")) { level = GameBoard.CHOOSE; }
      else { level = Integer.parseInt(temp); }

      if(level >= GameBoard.EASY && level <= GameBoard.EXTREME) {
        length = GameBoard.SIZE[level];
        width = GameBoard.SIZE[level];
        bombs = GameBoard.BOMBS[level];
      } else if(level == GameBoard.CHOOSE) {
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
            cont = false;
          } else { System.out.println(" too large an input..."); }
        }
      } else { System.out.println(" invalid input..."); }
    }
    JFrame frame = new JFrame();
    JFrame score = new JFrame();

    frame.setSize(length  * SQUARE_SIZE, width  * SQUARE_SIZE + 22);
    frame.setTitle("Mine Sweeper");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    score.setSize(SCORE_X, SCORE_Y);
    score.setTitle("Stats");

    MineSweeper game = new MineSweeper(frame, length, width, bombs);
    frame.add(game);

    ScoreBoard stats = new ScoreBoard(game);
    score.add(stats);

    frame.setVisible(true);
    score.setVisible(true);
  }
}
