package com.varunest.sparkbutton;

import android.content.Context;

import com.varunest.sparkbutton.helpers.Utils;

/**
 * @author varun on 07/07/16.
 */
public class SparkButtonBuilder {
    private SparkButton sparkButton;
    private Context context;

    public SparkButtonBuilder(Context context) {
        this.context = context;
        sparkButton = new SparkButton(context);
    }

    public SparkButtonBuilder setActiveImage(int resourceId) {
        sparkButton.imageResourceIdActive = resourceId;
        return this;
    }

    public SparkButtonBuilder setInactiveImage(int resourceId) {
        sparkButton.imageResourceIdInactive = resourceId;
        return this;
    }

    public SparkButtonBuilder setPrimaryColor(int color) {
        sparkButton.primaryColor = color;
        return this;
    }

    public SparkButtonBuilder setSecondaryColor(int color) {
        sparkButton.secondaryColor = color;
        return this;
    }

    public SparkButtonBuilder setImageSizePx(int px) {
        sparkButton.imageSize = px;
        return this;
    }

    public SparkButtonBuilder setImageSizeDp(int dp) {
        sparkButton.imageSize = Utils.dpToPx(context, dp);
        return this;
    }

    public SparkButtonBuilder setAnimationSpeed(float value) {
        sparkButton.animationSpeed = value;
        return this;
    }

    public SparkButton build() {
        sparkButton.init();
        return sparkButton;
    }
}
