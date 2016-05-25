package scores;

/**
 * A player in the ladder (maybe even in the top 10)
 */
public class BestPlayer {
    private String player;
    private int score;

    public BestPlayer() {
    }

    public BestPlayer(String name, int sc) {
        this.player = name;
        this.score = sc;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /** Allow comparizon of scores between this player, and another.
     * @param p The player that needs to be comparated to.
     * @return 0 if this player and p have the same score ; -1 if p has a greater score ; 1 if p has a lower score
     */
    public int compareTo(BestPlayer p) {
        if (p.getScore() == this.getScore()) return 0;
        else if (p.getScore() > this.getScore()) return -1;
        else return 1;
    }
}
