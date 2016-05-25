package scores;

import java.io.*;
import java.util.Scanner;

/**
 * Package used to test the scores.
 */
public class TestHighScore {
    private static String player = null;

    public TestHighScore() { }

    public static void main(String[] args) {
        // Ask for the name
        Scanner in = new Scanner(System.in);
        System.out.print("What's your player name ? ");
        player = in.nextLine();
        play();
    }

    private static void play() {
        do {
            // Get scores from ThingSpeak
            System.out.println("** Top scores : **");
            BestPlayer[] bestsP = HighScore.topTen();
            for (BestPlayer pl : bestsP) {
                // Display scores
                System.out.println(pl.getPlayer() + " : " + pl.getScore() + " points");
            }
            System.out.println("-- Play ! --");

            // Choose a score
            int score = -1;
            try {
                score = chooseScore();
            } catch (IOException e) {
                System.out.println("Error when reading score file. The file 'scoreSample.txt' must be present.");
                e.printStackTrace();
                System.exit(-1);
            }

            // Print the score
            System.out.println("Player " + player + " earned " + score + " points !");

            // Does this score deserve to appear in the ladder ?
            if (score > 0 && HighScore.getLowestTen() <= score) {
                // Yes !! :D
                System.out.println("Congratulations, your score is in the Top Ten ! Sending to the server ...");
                try {
                    if (player == null || player == "null") player = "Anonymous";
                    HighScore.sendScore(player, score);
                    System.out.println("Score sent to the server.");
                } catch (IOException e) {
                    System.out.println("Error when trying to send your score to the server.");
                    e.printStackTrace();
                }
            }
        } while(continuePlay());

    }

    public static boolean continuePlay() {
        Scanner in = new Scanner(System.in);
        System.out.print("Type '0' to stop playing.");
        return !in.nextLine().equals("0");
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
