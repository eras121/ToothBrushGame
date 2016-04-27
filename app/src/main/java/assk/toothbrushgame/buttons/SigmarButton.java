package assk.toothbrushgame.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import assk.toothbrushgame.R;
import assk.toothbrushgame.util.CustomFontHelper;


/**
 * Created by SK040133 on 3. 2. 2016.
 */
public class SigmarButton extends Button {
    public SigmarButton(Context context) {
        super(context);
    }

    public SigmarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
        this.setTextColor(getResources().getColor(R.color.textColorPrimary));
        this.setTextSize((float) 30);
    }

    public SigmarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        CustomFontHelper.setCustomFont(this, context, attrs);
        this.setTextColor(getResources().getColor(R.color.textColorPrimary));
    }
}
