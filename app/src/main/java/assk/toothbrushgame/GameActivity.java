package assk.toothbrushgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Kacer on 14. 4. 2016.
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView dirtLT;
    private ImageView dirtCT;
    private ImageView dirtRT;
    private ImageView dirtLB;
    private ImageView dirtCB;

    private ImageView foamLT;
    private ImageView foamCT;
    private ImageView foamRT;
    private ImageView foamLB;
    private ImageView foamCB;

    private ImageView emotion;

    private View object;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private float last_speed = 0;
    private final float MAX_SPEED = 20;
    private float displayWidth;
    private float displayHeight;
    private float pos_smile;
    private List<FallingObject> fallingObjects;
    private View fall_object;
    private Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        object = findViewById(R.id.object);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        displayWidth = metrics.widthPixels;
        displayHeight = metrics.heightPixels;
        int fallingObjectCount = (int) (displayWidth/30);

        fallingObjects = new ArrayList<FallingObject>(fallingObjectCount);

        fall_object = (ImageView) findViewById(R.id.fall_object);

        //create views dynamically


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (fall_object.getY() < displayHeight - fall_object.getHeight()) {
                    fall_object.setY(fall_object.getY()+2);
                    System.out.println(fall_object.getY());
                    try {
                        thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }});
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        thread.interrupt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        thread.start();
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
            } else if ((pos_smile + object.getWidth()) >= displayWidth ) {
                pos_smile = displayWidth - object.getWidth();
            }
            object.setX(pos_smile);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // creating objects and their behaviour
    private void falling_objects() {

    }


    private void object_fall(FallingObject falling_object) {
        // ak sa prida aj x, daju sa pocitat vektory, teda aj objekty budu padat bokom
        float y = falling_object.getY();
        float speed = falling_object.getSpeed();
        y = y + speed;
        falling_object.setY(y);
    }


}
