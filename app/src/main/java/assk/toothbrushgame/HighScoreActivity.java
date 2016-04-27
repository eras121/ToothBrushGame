package assk.toothbrushgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import assk.toothbrushgame.buttons.SigmarButton;

/**
 * Created by Tomas Kacer on 14. 4. 2016.
 */
public class HighScoreActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;

    private SigmarButton backBtn;

    private ScoreTab mScoreTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        textView1 = (TextView) findViewById(R.id.textView_1);
        textView2 = (TextView) findViewById(R.id.textView_2);
        textView3 = (TextView) findViewById(R.id.textView_3);
        textView4 = (TextView) findViewById(R.id.textView_4);
        textView5 = (TextView) findViewById(R.id.textView_5);

        backBtn = (SigmarButton) findViewById(R.id.button_high_score_back);

        mScoreTab = ScoreTab.get(getApplicationContext());

    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        fill_scores();
    }

    @Override
    public void onBackPressed() {
    }

    private void fill_scores() {

        textView1.setText(mScoreTab.getScoreText(0));
        textView2.setText(mScoreTab.getScoreText(1));
        textView3.setText(mScoreTab.getScoreText(2));
        textView4.setText(mScoreTab.getScoreText(3));
        textView5.setText(mScoreTab.getScoreText(4));


    }


    public void jumpToMainMenu(View v) {
        Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
        startActivity(intent);
    }

}
