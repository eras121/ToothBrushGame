package assk.toothbrushgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import assk.toothbrushgame.buttons.ParentButton;

/**
 * Created by Tomas Kacer on 14. 4. 2016.
 */
public class MainMenuActivity extends AppCompatActivity {

    private ParentButton playButton;
    private ParentButton voCoGoButton;
    private ParentButton highScoreButton;
    private ParentButton exitButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playButton = (ParentButton) findViewById(R.id.button_menu_play);
        voCoGoButton = (ParentButton) findViewById(R.id.button_vo_co_go);
        highScoreButton = (ParentButton) findViewById(R.id.button_high_score);
        exitButton = (ParentButton) findViewById(R.id.button_exit);

    }

    public void jumpToGameActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }

    public void jumpToVoCoGoActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), VoCoGoActivity.class);
        startActivity(intent);
    }

    public void jumpToHighScoreActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
        startActivity(intent);
    }

    public void jumpToExit(View v) {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }


}
