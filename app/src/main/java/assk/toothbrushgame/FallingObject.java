package assk.toothbrushgame;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Tomas Kacer on 15. 4. 2016.
 */
public class FallingObject extends ImageView implements Cloneable {

    private float speed;
    private boolean is_clean;
    private boolean is_existing;

    public FallingObject(Context context) {
        super(context);
    }

    public boolean is_existing() {
        return is_existing;
    }

    public void setIs_existing(boolean is_existing) {
        this.is_existing = is_existing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean is_clean() {
        return is_clean;
    }

    public void setIs_clean(boolean is_clean) {
        this.is_clean = is_clean;
    }

    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }


}
