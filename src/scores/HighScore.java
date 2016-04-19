package scores;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * V1 : We want to get the scores from ThingSpeak. API key provided in txt files.
 */
public class HighScore {

    /**
     * @return An unsorted list of scores, in CSV format
     * @throws IOException
     */
    public String[] getCsvScores() throws IOException {
        // Get HTML stream
        String html = Jsoup.connect("https://api.thingspeak.com/channels/"+ getKey('c') +"/feed.csv").get().outputSettings(new Document.OutputSettings().prettyPrint(false)).html();
        String[] feed = html.split(System.getProperty("line.separator"))[0].split("\\r?\\n");

        // We want only interesting lines : those that begins with a date
        // First : count the number of lines
        int nb = 0;
        for (String line : feed) {
            if (line.matches("^2[0-9]+.*")) {
                nb++;
            }
        }

        // Second : create a table containing those lines
        String[] scores = new String[nb];
        int it = 0;
        for (String line : feed) {
            if (line.matches("^2[0-9]+.*")) {
                scores[it] = line;
                it++;
            }
        }

        // Return those lines (in csv format)
        return scores;
    }

    /**
     * @return An unsorted list of scores
     * @throws IOException
     */
    public String[] getScores() {
        // Get scores - SELECT * FROM ThingSpeak
        String[] csvScores = null;
        try {
            csvScores = getCsvScores();
        } catch (IOException e) {
            System.out.println("Can't get scores from ThingSpeak");
            e.printStackTrace();
        }

        // We want only interesting things in these lines : the player, and the score
        int it = 0;
        String[] scores = new String[csvScores.length];
        for (String line : csvScores) {
            String[] sc = line.split(",");
            scores[it] = sc[3] + " : " + sc[2] + " points";
            it++;
        }

        // Return those lines (in csv format)
        return scores;
    }

    /**
     * @param opt Can be 'r' for read, 'w' for write, or 'c' for channel ID
     * @return Return the corresponding key for ThingSpeak
     * @throws IOException
     */
    public String getKey(char opt) throws IOException {
        String key = null;
        String file = null;

        // A key ? But which key ?
        if (opt == 'r') {
            file = "api_write.txt";
        }
        else if (opt == 'w') {
            file = "api_read.txt";
        }
        else if (opt == 'c') {
            file = "api_channel.txt";
        }
        else return "";

        // Open the API file
        FileInputStream fstream = null;
        fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        // Get the key
        key = br.readLine();

        // Close the file
        br.close();

        // Return the key
        return key;
    }
}
