package assk.toothbrushgame;

import android.content.Context;

/**
 * Created by Tomas Kacer on 27. 4. 2016.
 */
public class ScoreTab {

    private static ScoreTab sScoreTab;

    private Context mContext;
    private Score[] mScores;

    private int size;

    public static ScoreTab get(Context context) {
        if (sScoreTab == null) {
            sScoreTab = new ScoreTab(context);
        }
        return sScoreTab;
    }

    private ScoreTab(Context context) {
        mContext = context.getApplicationContext();
        size = 5;
        mScores = new Score[size];
    }

    public void insertScore(Score score) {
        for (int i = 0; i < mScores.length; i++) {
            Score scoreOld = mScores[i];
            if ( (scoreOld == null) || (scoreOld.getScorePoints() <  score.getScorePoints())) {
                for (int j = mScores.length - 1; j > i ; j--){
                    mScores[j] = mScores[j-1];
                }
                mScores[i] = score;
                break;
            }
        }
    }

    public Score getScore(int i) {
        return mScores[i];
    }

    public String getScoreText(int i) {
        String text = "";
        Score score = getScore(i);
        if (score == null) {
            text = text.concat(Integer.toString(i+1) + ".");
            return text;
        }
        text = text.concat(Integer.toString(i+1) + ". \t");
        text = text.concat(score.getScorePoints() + " \t");
        text = text.concat(score.getName());

        return text;
    }

    public int getSize() {
        return size;
    }

}
