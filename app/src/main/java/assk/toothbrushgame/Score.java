package assk.toothbrushgame;

/**
 * Created by Tomas Kacer on 27. 4. 2016.
 */
public class Score {
    private String name;
    private int scorePoints;

    public Score(String name, int scorePoints) {
        this.name = name;
        this.scorePoints = scorePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(int score) {
        this.scorePoints = score;
    }
}
