package scores;

import java.io.*;
import java.util.Scanner;

/**
 * Package used to test the scores.
 */
public class TestHighScore {
    private static String player = null;
    private static int score;

    public TestHighScore() { }

    public static void main(String[] args) {
        HighScore hs = new HighScore();
        try {
            String[] scores = hs.getScores();
            for (String s : scores) {
                System.out.println(s);
                System.out.println("---");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ask for the name
        Scanner in = new Scanner(System.in);
        System.out.print("What's your player name ? ");
        player = in.nextLine();

        // Choose a score
        try {
            score = chooseScore();
        } catch (IOException e) {
            System.out.println("Error when reading score file !!");
            e.printStackTrace();
        }

        // Print the score
        System.out.println("Player " + player + " earned " + score + " points !");
    }

    public static int chooseScore() throws IOException {
        int nbScores = 0;

        // Open the score file
        FileInputStream fstream = new FileInputStream("scoreSample.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        // How many score in the file ? (1 score/line)
        while (br.readLine() != null) {
            nbScores++;
        }

        // Save all scores in a table
        int[] listScores = new int[nbScores];
        int it = 0;
        // Get back to the beginning of the file, you cursor
        fstream.getChannel().position(0);
        br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            listScores[it] = Integer.parseInt(strLine);
            it++;
        }

        // Close the file
        br.close();

        // Select and return a random score among the ones read from the file
        return listScores[(int) (Math.random()*nbScores)];
    }
}
