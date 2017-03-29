package com.varunest.sparkbutton.helpers;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;

public class Utils {
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static int darkenColor(int color, float multiplier){
        float[] hsv = new float[3];

        Color.colorToHSV(color, hsv);
        hsv[2] *= multiplier; // value component
        int darkColor = Color.HSVToColor(hsv);
        return darkColor;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}