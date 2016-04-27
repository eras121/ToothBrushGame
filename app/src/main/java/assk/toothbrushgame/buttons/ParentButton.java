package assk.toothbrushgame.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import assk.toothbrushgame.R;


/**
 * Created by SK040133 on 4. 2. 2016.
 */
public class ParentButton extends Button {
    public ParentButton(Context context) {
        super(context);
    }

    public ParentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTextColor(getResources().getColor(R.color.colorFontBlue));
    }

    public ParentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
