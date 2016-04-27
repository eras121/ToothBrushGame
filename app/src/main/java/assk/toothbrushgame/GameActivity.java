package assk.toothbrushgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tomas Kacer on 14. 4. 2016.
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private static final int SADNESS_HAPPY = 0;
    private static final int SADNESS_OK = 1;
    private static final int SADNESS_SAD = 2;
    private static final int SADNESS_GAMEOVER = 3;

    private ImageView dirtLT;
    private ImageView dirtCT;
    private ImageView dirtRT;
    private ImageView dirtLB;
    private ImageView dirtCB;

    private ImageView emotion;
    private static int emotion_saddnes;

    private View object;

    private TextView timeView;
    private TextView startTimeView;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private float last_speed = 0;
    private final float MAX_SPEED = 20;
    private float displayWidth;
    private float displayHeight;
    private float pos_smile;
    private List<FallingObject> fallingObjects;
    private RelativeLayout relativeLayout;
    private int fallingObjectCount;
    private int start_time;

    private boolean game_on;
    private boolean game_over;
    private int waitingTime;
    private int scorePoints;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        dirtLT = (ImageView) findViewById(R.id.dirt_lt);
        dirtCT = (ImageView) findViewById(R.id.dirt_ct);
        dirtRT = (ImageView) findViewById(R.id.dirt_rt);
        dirtLB = (ImageView) findViewById(R.id.dirt_lb);
        dirtCB = (ImageView) findViewById(R.id.dirt_cb);

        object = findViewById(R.id.object);
        startTimeView = (TextView) findViewById(R.id.start_textView);

        emotion = (ImageView) findViewById(R.id.emotion);
        emotion_saddnes = SADNESS_HAPPY;
        setSadness(emotion_saddnes);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        displayWidth = metrics.widthPixels;
        displayHeight = metrics.heightPixels;
        fallingObjectCount = (int) (displayWidth/150);
        relativeLayout = (RelativeLayout) findViewById(R.id.falling_pane);

        fallingObjects = new ArrayList<>(fallingObjectCount);
        relativeLayout = (RelativeLayout) findViewById(R.id.falling_pane);

        timeView = (TextView) findViewById(R.id.score_textView);
        game_on = false;
        game_over = false;
        scorePoints = 0;

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        fillFallingObjects();
        object_fall();


        // start sequence 3...2...1...START
        new Thread(new Runnable() {
            @Override
            public void run() {

                waitingTime = 3;
                while (waitingTime != 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startTimeView.setText(Integer.toString(waitingTime));
                            waitingTime--;
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (waitingTime == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startTimeView.setText("START");
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTimeView.setVisibility(View.INVISIBLE);
                    }
                });

                game_on = true;
                start_time = (int) (System.currentTimeMillis()/1000);
            }
        }).start();
    }

    /**
     * Called when sensor values have changed.
     * <p>See {@link SensorManager SensorManager}
     * for details on possible sensor types.
     * <p>See also {@link SensorEvent SensorEvent}.
     * <p/>
     * <p><b>NOTE:</b> The application doesn't own the
     * {@link SensorEvent event}
     * object passed as a parameter and therefore cannot hold on to it.
     * The object may be part of an internal pool and may be reused by
     * the framework.
     *
     * @param event the {@link SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {


        if (game_on) {

            Sensor mySensor = event.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // min a max y is from -10 to +10, 0 is the middle

                float actual_pos = object.getX();
                float speed = last_speed + y;
                if (Math.abs(speed) > MAX_SPEED) {
                    if (speed < 0) {
                        speed = -MAX_SPEED;
                    } else {
                        speed = MAX_SPEED;
                    }
                }
                pos_smile = actual_pos + speed;

                last_speed = speed;

                if (pos_smile <= 0) {
                    pos_smile = 0;
                } else if ((pos_smile + object.getWidth()) >= displayWidth) {
                    pos_smile = displayWidth - object.getWidth();
                }
                object.setX(pos_smile);

                if (!game_over) {
                    object_fall();
                    scorePoints = (int) (System.currentTimeMillis() / 1000 - start_time);
                }
                timeView.setText(Integer.toString(scorePoints));

            }

        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void setSadness(int sadness) {
        switch (sadness) {
            case SADNESS_HAPPY:
                emotion.setImageDrawable(getResources().getDrawable(R.drawable.emotion_happy));

                dirtLT.setVisibility(View.INVISIBLE);
                dirtCT.setVisibility(View.INVISIBLE);
                dirtRT.setVisibility(View.INVISIBLE);
                dirtLB.setVisibility(View.INVISIBLE);
                dirtCB.setVisibility(View.INVISIBLE);
                break;
            case SADNESS_OK:
                emotion.setImageDrawable(getResources().getDrawable(R.drawable.emotion_excited));

                dirtLT.setVisibility(View.INVISIBLE);
                dirtCT.setVisibility(View.INVISIBLE);
                dirtRT.setVisibility(View.VISIBLE);
                dirtLB.setVisibility(View.VISIBLE);
                dirtCB.setVisibility(View.VISIBLE);
                break;
            case SADNESS_SAD:

                emotion.setImageDrawable(getResources().getDrawable(R.drawable.emotion_sad));

                dirtLT.setVisibility(View.VISIBLE);
                dirtCT.setVisibility(View.VISIBLE);
                dirtRT.setVisibility(View.VISIBLE);
                dirtLB.setVisibility(View.VISIBLE);
                dirtCB.setVisibility(View.VISIBLE);
                break;
            case SADNESS_GAMEOVER:
                game_over = true;
                saveToHighScore();
                break;
        }


    }

    // creating objects and their behaviour

    private void fillFallingObjects() {
        if (fallingObjects.isEmpty()) {
            for (int j = 0; j < fallingObjectCount; j++) {
                FallingObject imageView = new FallingObject(this);
                fallingObjects.add(j, imageView);
                relativeLayout.addView(imageView);
                imageView.setId(j);

                imageView.setLayoutParams(new RelativeLayout.LayoutParams(48, 48));
                relativeLayout.invalidate();
                createFallingObject(imageView);

                imageView.setVisibility(View.INVISIBLE);
                imageView.setIs_clean(false);
                imageView.setIs_existing(false);
            }
        }
    }

    private FallingObject createFallingObject(FallingObject imageView) {
//        boolean willBeExisting = (Math.random() < 0.5) ? false : true;
        boolean willBeExisting = true;
        if (willBeExisting) {
            imageView.setX((float) (Math.random() * displayWidth));
            imageView.setY(0);
            imageView.setSpeed((float) (Math.random() * 3));
            switch (imageView.getId() % 5) {
                case 0:
                    imageView.setImageResource(R.drawable.dirt1);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.dirt2);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.dirt3);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.dirt4);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.dirt5);
                    break;
                default:
                    imageView.setImageResource(R.drawable.dirt1);
                    break;
            }
        }
        return imageView;
    }

    private void object_fall() {

        if (!game_over) {

            // ak sa prida aj x, daju sa pocitat vektory, teda aj objekty budu padat bokom
            for (FallingObject fallingObject : fallingObjects) {
                if (fallingObject.is_existing()) {
                    float y = fallingObject.getY();
                    float speed = fallingObject.getSpeed();
                    y = y + speed;
                    fallingObject.setY(y);

                    float x_emotikon = object.getX();
                    float width_emotikon = object.getWidth();

                    float height_emotikon = displayHeight - (object.getTop() + 15); //15 is the bottom space

                    float yBottom = y + fallingObject.getBottom();
                    if (yBottom >= height_emotikon && yBottom < displayHeight) {
                        float diff_x = fallingObject.getX() - x_emotikon;
                        if (diff_x < width_emotikon && diff_x > 0) {
                            emotion_saddnes++;
                            setSadness(emotion_saddnes);
                            fallingObject.setIs_existing(false);
                            fallingObject.setVisibility(View.INVISIBLE);
                        }

                    } else if (y >= displayHeight) {
                        fallingObject.setIs_existing(false);
                        fallingObject.setVisibility(View.INVISIBLE);
                    }

                } else {
                    boolean willBeExisting = (Math.random() < 0.8) ? false : true;
//                    if (willBeExisting) {
                    createFallingObject(fallingObject);
                    fallingObject.setIs_existing(true);
                    fallingObject.setVisibility(View.VISIBLE);
//                    }
                }
            }
        }
    }

    private void saveToHighScore() {
        Score lastScore = ScoreTab.get(getApplicationContext()).getScore(4);
        if ((lastScore == null) || (lastScore.getScorePoints() < scorePoints)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Skóre");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Score score = new Score(input.getText().toString(), scorePoints);
                    ScoreTab.get(getApplicationContext()).insertScore(score);
                    Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }


}
