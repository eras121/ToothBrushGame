package assk.toothbrushgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import assk.toothbrushgame.buttons.SigmarButton;

/**
 * Created by Tomas Kacer on 14. 4. 2016.
 */
public class VoCoGoActivity extends AppCompatActivity {

    private SigmarButton backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vo_co_go);

        backBtn = (SigmarButton) findViewById(R.id.button_vo_co_go_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

}
