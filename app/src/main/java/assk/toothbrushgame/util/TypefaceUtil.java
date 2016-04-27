package assk.toothbrushgame.util;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SK040133 on 1. 2. 2016.
 */
public class TypefaceUtil {

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     * @param context to work with assets
     * @param defaultFontNameToOverride for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                Map<String, Typeface> newMap = new HashMap<String, Typeface>();
                newMap.put("sans-serif", customFontTypeface);
                try {
                    final Field staticField = Typeface.class
                            .getDeclaredField("sSystemFontMap");
                    staticField.setAccessible(true);
                    staticField.set(null, newMap);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    final Field staticField = Typeface.class
                            .getDeclaredField(defaultFontNameToOverride);
                    staticField.setAccessible(true);
                    staticField.set(null, customFontTypeface);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            // TODO: log
            System.out.println("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }
}
