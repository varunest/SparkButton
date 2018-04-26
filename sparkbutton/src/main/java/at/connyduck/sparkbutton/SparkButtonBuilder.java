package at.connyduck.sparkbutton;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;

import at.connyduck.sparkbutton.helpers.Utils;

/**
 * @author varun on 07/07/16.
 */
@SuppressWarnings("unused")
public class SparkButtonBuilder {
    private SparkButton sparkButton;
    private Context context;

    public SparkButtonBuilder(Context context) {
        this.context = context;
        sparkButton = new SparkButton(context);
    }

    public SparkButtonBuilder setActiveImage(@DrawableRes int resourceId) {
        sparkButton.setActiveImage(resourceId);
        return this;
    }

    public SparkButtonBuilder setInactiveImage(@DrawableRes int resourceId) {
        sparkButton.setInactiveImage(resourceId);
        return this;
    }

    public SparkButtonBuilder setPrimaryColor(@ColorInt int color) {
        sparkButton.setPrimaryColor(color);
        return this;
    }

    public SparkButtonBuilder setSecondaryColor(@ColorInt int color) {
        sparkButton.setSecondaryColor(color);
        return this;
    }

    public SparkButtonBuilder setImageSizePx(@Px int px) {
        sparkButton.setImageSize(px);
        return this;
    }

    public SparkButtonBuilder setImageSizeDp(int dp) {
        sparkButton.setImageSize(Utils.dpToPx(context, dp));
        return this;
    }

    public SparkButtonBuilder setAnimationSpeed(float speed) {
        sparkButton.setAnimationSpeed(speed);
        return this;
    }

    public SparkButton build() {
        sparkButton.init();
        return sparkButton;
    }
}
