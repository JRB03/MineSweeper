import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
the score board
@author Jack Basinet
@version 2.2.20
*/
public class ScoreBoard extends JComponent implements ActionListener
{
  public static final int TICK = 20;
  public static final String LOSE = "You Lose :(";
  public static final String WIN = "You Win! :)";

  private Timer timer;
  private int sec;
  private int min;
  private int hrs;
  private String time;

  private boolean restart;
  private int wait;

  private int bombs_rem;
  private String remain;

  private MineSweeper game;

  /**
  constructor
  @param game the game
  */
  public ScoreBoard(MineSweeper game)
  {
    timer = new Timer(TICK, this);
    timer.start();
    restart = false;
    wait = 0;

    sec = 0;
    min = 0;
    hrs = 0;

    this.game = game;
    bombs_rem = game.flags;
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    g.drawString(time, 20, 20);
    g.drawString(remain, 20, 35);


    if(game.over) { g.drawString(LOSE, 90, 20); }
    if(game.win) { g.drawString(WIN, 90, 20); }
  }

  /**
  runs everytime the timer clicks
  @param e the action event
  */
  public void actionPerformed(ActionEvent e)
  {
    if(!game.over && !game.win && restart)
    {
      sec = 0;
      min = 0;
      hrs = 0;
      restart = false;
    }
    else if(!game.over && !game.win && wait == 50)
    {
     sec++;
     hrs = (hrs + min / 60);
     min = min % 60 + sec / 60;
     sec = (sec % 60);
     wait = 0;
    }
    else if(!game.over && !game.win) { wait++; }
    else
    { restart = true; }

     if(hrs < 10)
     {
       if(min < 10)
       {
         if(sec <10) { time = "0" + hrs + ":0" + min + ":0" + sec; }
         else { time = "0" + hrs + ":0" + min + ":" + sec; }
       }
       else
       {
         if(sec <10) { time = "0" + hrs + ":" + min + ":0" + sec; }
         else { time = "0" + hrs + ":" + min + ":" + sec; }
       }
     }
     else
     {
       if(min < 10)
       {
         if(sec <10) { time =  hrs + ":0" + min + ":0" + sec; }
         else { time = hrs + ":0" + min + ":" + sec; }
       }
       else
       {
         if(sec <10) { time = hrs + ":" + min + ":0" + sec; }
         else { time = hrs + ":" + min + ":" + sec; }
       }
     }

     bombs_rem = game.flags;
     remain = "Bombs left: " + bombs_rem;

     repaint();
  }
}
