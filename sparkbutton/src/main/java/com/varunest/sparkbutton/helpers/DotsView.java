package com.varunest.sparkbutton.helpers;

import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

/**
 * @author Miroslaw Stanek on 21.12.2015.
 * @modifier Varun
 */
public class DotsView extends View {
    private static final int DOTS_COUNT = 10;
    private static final int OUTER_DOTS_POSITION_ANGLE = 360 / DOTS_COUNT;

    private int color1 = 0xFFFFC107;
    private int color2 = 0xFFFF9800;
    private int color3 = 0xFFFF5722;
    private int color4 = 0xFFF44336;

    private final Paint[] circlePaints = new Paint[4];

    private int centerX;
    private int centerY;

    private float maxOuterDotsRadius;
    private float maxInnerDotsRadius;
    private float maxDotSize;

    private float currentProgress = 0;

    private float currentRadius1 = 0;
    private float currentDotSize1 = 0;

    private float currentDotSize2 = 0;
    private float currentRadius2 = 0;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public DotsView(Context context) {
        super(context);
        init();
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DotsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        maxDotSize = Utils.dpToPx(getContext(), 4);
        for (int i = 0; i < circlePaints.length; i++) {
            circlePaints[i] = new Paint();
            circlePaints[i].setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxOuterDotsRadius = w / 2 - maxDotSize * 2;
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawOuterDotsFrame(canvas);
        drawInnerDotsFrame(canvas);
    }

    public void setMaxDotSize(int pxUnits) {
        maxDotSize = pxUnits;
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + currentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize1, circlePaints[i % circlePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius2 * Math.cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            int cY = (int) (centerY + currentRadius2 * Math.sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize2, circlePaints[(i + 1) % circlePaints.length]);
        }
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;

        updateInnerDotsPosition();
        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();

        postInvalidate();
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    private void updateInnerDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0, 0.3f, 0.f, maxInnerDotsRadius);
        } else {
            this.currentRadius2 = maxInnerDotsRadius;
        }

        if (currentProgress < 0.2) {
            this.currentDotSize2 = maxDotSize;
        } else if (currentProgress < 0.5) {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.2f, 0.5f, maxDotSize, 0.3 * maxDotSize);
        } else {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, maxDotSize * 0.3f, 0);
        }

    }

    private void updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.0f, 0.3f, 0, maxOuterDotsRadius * 0.8f);
        } else {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.3f, 1f, 0.8f * maxOuterDotsRadius, maxOuterDotsRadius);
        }

        if (currentProgress < 0.7) {
            this.currentDotSize1 = maxDotSize;
        } else {
            this.currentDotSize1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.7f, 1f, maxDotSize, 0);
        }
    }

    private void updateDotsPaints() {
        if (currentProgress < 0.5f) {
            float progress = (float) Utils.mapValueFromRangeToRange(currentProgress, 0f, 0.5f, 0, 1f);
            circlePaints[0].setColor((Integer) argbEvaluator.evaluate(progress, color1, color2));
            circlePaints[1].setColor((Integer) argbEvaluator.evaluate(progress, color2, color3));
            circlePaints[2].setColor((Integer) argbEvaluator.evaluate(progress, color3, color4));
            circlePaints[3].setColor((Integer) argbEvaluator.evaluate(progress, color4, color1));
        } else {
            float progress = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, 0, 1f);
            circlePaints[0].setColor((Integer) argbEvaluator.evaluate(progress, color2, color3));
            circlePaints[1].setColor((Integer) argbEvaluator.evaluate(progress, color3, color4));
            circlePaints[2].setColor((Integer) argbEvaluator.evaluate(progress, color4, color1));
            circlePaints[3].setColor((Integer) argbEvaluator.evaluate(progress, color1, color2));
        }
    }

    private void updateDotsAlpha() {
        float progress = (float) Utils.clamp(currentProgress, 0.6f, 1f);
        int alpha = (int) Utils.mapValueFromRangeToRange(progress, 0.6f, 1f, 255, 0);
        circlePaints[0].setAlpha(alpha);
        circlePaints[1].setAlpha(alpha);
        circlePaints[2].setAlpha(alpha);
        circlePaints[3].setAlpha(alpha);
    }

    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };

    public void setColors(int startColor, int endColor) {
        color1 = startColor;
        color2 = Utils.darkenColor(color1, 1.1f);
        color4 = endColor;
        color3 = Utils.darkenColor(color4, 1.1f);
    }
}