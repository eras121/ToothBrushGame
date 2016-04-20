package assk.toothbrushgame;

/**
 * Created by Tomas Kacer on 15. 4. 2016.
 */
public class FallingObject implements Cloneable {

    private float x;
    private float y;
    private float speed;
    private int id;
    private boolean is_clean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
