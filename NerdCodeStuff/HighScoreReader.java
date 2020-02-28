import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class HighScoreReader
{
    public static final String FILENAME = "../HighScores.txt";

    /** the list of scores */
    private ArrayList<String> scores;

    /**
    reads and adds a score to the High Score doc
    @param score the score to be added in
    */
    public void readFile(String score) throws IOException
    {
      scores = new ArrayList<>();

      Files.lines(Paths.get(FILENAME)).map(s -> s.trim()).filter(s -> !s.isEmpty()).forEach(n -> scores.add(n));

      scores.add(score);
    }

    /**
    swaps two ints in an array for sortScore
    @param list the list of intScores
    @param a one idx to be swapped
    @param b the other idx to be swapped
    */
    static void swap(ArrayList<Integer> list, int a, int b)
   {
      Integer tmp;
      tmp = list.get(a);
      list.set(a , list.get(b));
      list.set(b, tmp);
   }

   /**
   gets the score of a high score String
   @param str the string to be broken down to an int
   @param Integer the score of str
   */
   static Integer getIntScore(String str)
   {
     boolean inte = true;
     int idx = 0;
     String num = "";
     while(inte)
     {
       if(str.charAt(idx) > 47 && str.charAt(idx) < 58)
       {
         num += str.charAt(idx);
         idx++;
       }
       else { inte = false; }
     }
     Integer sco = Integer.parseInt(num);
     return sco;
   }

   /**
   sorts the scores List
   */
   public void sortScores()
   {
      ArrayList<Integer> intScores = new ArrayList<>();
      for(String s: scores)
      {
        Integer temp = getIntScore(s);
        intScores.add(temp);
      }

      int n = intScores.size();
      for(int swapIdx = 0; swapIdx < n; swapIdx++)
      {
         int idx = swapIdx;
         Integer idxVal = intScores.get(swapIdx);
         String idxValS = scores.get(swapIdx);

         while(idx > 0 && intScores.get(idx - 1) > idxVal)
         {
            intScores.set(idx, intScores.get(idx - 1));
            scores.set(idx, scores.get(idx - 1));
            idx = idx - 1;
         }
         intScores.set(idx, idxVal);
         scores.set(idx, idxValS);
      }
    }

    /**
    writes the sorted score list into the High Score doc
    */
    public void writeScores() throws IOException
    {
      String content = "";
      for(String s: scores)
      {
        content += s + "\n";
      }
      Files.write(Paths.get(FILENAME), content.getBytes(), StandardOpenOption.CREATE);
    }

    /**
    updates the score doc
    @param time the new score's time
    @param diff the new score's difficulty level
    */
    public void updateScores(String time, String diff) throws IOException
    {
      String score = time + " " + diff;
      readFile(score);
      sortScores();
      writeScores();
    }
}
