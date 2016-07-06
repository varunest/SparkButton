package com.varunest.sparkbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.varunest.sparkbutton.heplers.CircleView;
import com.varunest.sparkbutton.heplers.DotsView;

/**
 * @author varun 7th July 2016
 */
public class SparkButton extends FrameLayout {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private ImageView vImageView;
    private int imageHeight;
    private int imageWidth;
    private int imageResourceId = -1;

    private DotsView vDotsView;
    private int dotsSize;

    private CircleView vCircle;
    private int circleSize;

    private int startColor = 0xFFFF5722;
    private int endColor = 0xFFFFC107;

    private AnimatorSet animatorSet;

    public SparkButton(Context context) {
        super(context);
        init();
    }

    public SparkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        getStuffFromXML(attrs);
        init();
    }

    public SparkButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStuffFromXML(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SparkButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getStuffFromXML(attrs);
        init();
    }

    public void setImageDrawable(Drawable drawable) {
        vImageView.setBackgroundDrawable(drawable);
    }

    public void setColors(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    private void getStuffFromXML(AttributeSet attr) {
        TypedArray a = getContext().obtainStyledAttributes(attr, R.styleable.sparkbutton);
        imageHeight = a.getDimensionPixelOffset(R.styleable.sparkbutton_sparkbutton_height, 100);
        imageWidth = a.getDimensionPixelOffset(R.styleable.sparkbutton_sparkbutton_width, 100);
        imageResourceId = a.getResourceId(R.styleable.sparkbutton_sparkbutton_img_src, -1);
        dotsSize = a.getDimensionPixelOffset(R.styleable.sparkbutton_sparkbutton_dots_size, 250);
        circleSize = a.getDimensionPixelOffset(R.styleable.sparkbutton_sparkbutton_circle_size, 150);
        startColor = a.getResourceId(R.styleable.sparkbutton_sparkbutton_secondarycolor, -1);
        if (startColor != -1) {
            startColor = getResources().getColor(startColor);
        } else {
            startColor = 0xFFFF5722;
        }
        endColor = a.getResourceId(R.styleable.sparkbutton_sparkbutton_primarycolor, -1);
        if (endColor != -1) {
            endColor = getResources().getColor(endColor);
        } else {
            endColor = 0xFFFFC107;
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_spark_button, this, true);
        vCircle = (CircleView) findViewById(R.id.vCircle);
        vCircle.setColors(startColor, endColor);
        vCircle.getLayoutParams().height = circleSize;
        vCircle.getLayoutParams().width = circleSize;

        vDotsView = (DotsView) findViewById(R.id.vDotsView);
        vDotsView.setColors(startColor, endColor);
        vDotsView.getLayoutParams().width = dotsSize;
        vDotsView.getLayoutParams().height = dotsSize;

        vImageView = (ImageView) findViewById(R.id.ivImage);
        vImageView.getLayoutParams().height = imageHeight;
        vImageView.getLayoutParams().width = imageWidth;
        if (imageResourceId != -1) {
            vImageView.setImageDrawable(getResources().getDrawable(imageResourceId));
        }
    }

    public void enableStateAnimation() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        vImageView.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                        setPressed(true);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        float y = event.getY();
                        boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                        if (isPressed() != isInside) {
                            setPressed(isInside);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        vImageView.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                        if (isPressed()) {
                            performClick();
                            setPressed(false);
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void playAnimation() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }

        vImageView.animate().cancel();
        vImageView.setScaleX(0);
        vImageView.setScaleY(0);
        vCircle.setInnerCircleRadiusProgress(0);
        vCircle.setOuterCircleRadiusProgress(0);
        vDotsView.setCurrentProgress(0);

        animatorSet = new AnimatorSet();

        ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(vCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        outerCircleAnimator.setDuration(250);
        outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(vCircle, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        innerCircleAnimator.setDuration(200);
        innerCircleAnimator.setStartDelay(200);
        innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(vImageView, ImageView.SCALE_Y, 0.2f, 1f);
        starScaleYAnimator.setDuration(350);
        starScaleYAnimator.setStartDelay(250);
        starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(vImageView, ImageView.SCALE_X, 0.2f, 1f);
        starScaleXAnimator.setDuration(350);
        starScaleXAnimator.setStartDelay(250);
        starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(vDotsView, DotsView.DOTS_PROGRESS, 0, 1f);
        dotsAnimator.setDuration(900);
        dotsAnimator.setStartDelay(50);
        dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

        animatorSet.playTogether(
                outerCircleAnimator,
                innerCircleAnimator,
                starScaleYAnimator,
                starScaleXAnimator,
                dotsAnimator
        );

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                vCircle.setInnerCircleRadiusProgress(0);
                vCircle.setOuterCircleRadiusProgress(0);
                vDotsView.setCurrentProgress(0);
                vImageView.setScaleX(1);
                vImageView.setScaleY(1);
            }
        });

        animatorSet.start();
    }
}