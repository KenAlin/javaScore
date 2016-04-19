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
     * @return A list of scores
     * @throws IOException
     */
    public String[] getScores() throws IOException {
        String html = Jsoup.connect("https://api.thingspeak.com/channels/"+ getKey('c') +"/feed.csv").get().outputSettings(new Document.OutputSettings().prettyPrint(false)).html();
        String[] feed = html.split(System.getProperty("line.separator"))[0].split("\\r?\\n");
        return feed;
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
