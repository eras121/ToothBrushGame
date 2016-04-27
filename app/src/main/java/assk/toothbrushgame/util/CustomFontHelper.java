package assk.toothbrushgame.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import assk.toothbrushgame.R;


/**
 * Created by SK040133 on 3. 2. 2016.
 */
public class CustomFontHelper {

    /**
     * Sets a font on a textview based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     * @param textview
     * @param context
     * @param attrs
     */
    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String font = a.getString(R.styleable.CustomFont_font);
        setCustomFont(textview, font, context);
        a.recycle();
    }

    /**
     * Sets a font on a textview
     * @param textview
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        if(tf != null) {
            textview.setTypeface(tf);
        }
    }
}
