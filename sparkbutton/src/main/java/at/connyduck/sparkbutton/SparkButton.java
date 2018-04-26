/* Copyright 2017 Varun, 2018 Conny Duck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package at.connyduck.sparkbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import at.connyduck.sparkbutton.helpers.CircleView;
import at.connyduck.sparkbutton.helpers.DotsView;
import at.connyduck.sparkbutton.helpers.Utils;

@SuppressWarnings("unused")
public class SparkButton extends FrameLayout implements View.OnClickListener {
    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private static final int INVALID_RESOURCE_ID = -1;
    private static final float DOTVIEW_SIZE_FACTOR = 3;
    private static final float DOTS_SIZE_FACTOR = .08f;
    private static final float CIRCLEVIEW_SIZE_FACTOR = 1.4f;

    private @DrawableRes int imageResourceIdActive = INVALID_RESOURCE_ID;
    private @DrawableRes int imageResourceIdInactive = INVALID_RESOURCE_ID;

    private @Px int imageSize;
    private @ColorInt int primaryColor;
    private @ColorInt int secondaryColor;
    private @ColorInt int activeImageTint;
    private @ColorInt int inActiveImageTint;

    private DotsView dotsView;
    private CircleView circleView;
    private ImageView imageView;

    private float animationSpeed = 1;
    private boolean isChecked = false;

    private AnimatorSet animatorSet;
    private SparkEventListener listener;

    SparkButton(Context context) {
        super(context);
    }

    public SparkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFromXML(attrs);
        init();
    }

    public SparkButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromXML(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SparkButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFromXML(attrs);
        init();
    }

    void init() {

        int circleSize = (int) (imageSize * CIRCLEVIEW_SIZE_FACTOR);
        int dotsSize = (int) (imageSize * DOTVIEW_SIZE_FACTOR);

        dotsView = new DotsView(getContext());
        LayoutParams dotsViewLayoutParams = new LayoutParams(dotsSize, dotsSize);
        dotsViewLayoutParams.gravity = Gravity.CENTER;
        dotsView.setLayoutParams(dotsViewLayoutParams);

        dotsView.setColors(secondaryColor, primaryColor);
        dotsView.setMaxDotSize((int) (imageSize * DOTS_SIZE_FACTOR));

        addView(dotsView);

        circleView = new CircleView(getContext());
        LayoutParams circleViewLayoutParams = new LayoutParams(circleSize, circleSize);
        circleViewLayoutParams.gravity = Gravity.CENTER;
        circleView.setLayoutParams(circleViewLayoutParams);

        circleView.setColors(secondaryColor, primaryColor);
        circleView.getLayoutParams().height = circleSize;
        circleView.getLayoutParams().width = circleSize;

        addView(circleView);

        imageView = new ImageView(getContext());
        LayoutParams imageViewLayoutParams = new LayoutParams(imageSize, imageSize);
        imageViewLayoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageViewLayoutParams);

        addView(imageView);

        if (imageResourceIdInactive != INVALID_RESOURCE_ID) {
            // should load inactive img first
            imageView.setImageResource(imageResourceIdInactive);
            imageView.setColorFilter(inActiveImageTint, PorterDuff.Mode.SRC_ATOP);
        } else if (imageResourceIdActive != INVALID_RESOURCE_ID) {
            imageView.setImageResource(imageResourceIdActive);
            imageView.setColorFilter(activeImageTint, PorterDuff.Mode.SRC_ATOP);
        } else {
            throw new IllegalArgumentException("One of Inactive/Active Image Resources is required!");
        }
        setOnTouchListener();
        setOnClickListener(this);

    }

    /**
     * Call this function to start spark animation
     */
    public void playAnimation() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }

        imageView.animate().cancel();
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        circleView.setInnerCircleRadiusProgress(0);
        circleView.setOuterCircleRadiusProgress(0);
        dotsView.setCurrentProgress(0);

        animatorSet = new AnimatorSet();

        ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        outerCircleAnimator.setDuration((long) (250 / animationSpeed));
        outerCircleAnimator.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
        innerCircleAnimator.setDuration((long) (200 / animationSpeed));
        innerCircleAnimator.setStartDelay((long) (200 / animationSpeed));
        innerCircleAnimator.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(imageView, ImageView.SCALE_Y, 0.2f, 1f);
        starScaleYAnimator.setDuration((long) (350 / animationSpeed));
        starScaleYAnimator.setStartDelay((long) (250 / animationSpeed));
        starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(imageView, ImageView.SCALE_X, 0.2f, 1f);
        starScaleXAnimator.setDuration((long) (350 / animationSpeed));
        starScaleXAnimator.setStartDelay((long) (250 / animationSpeed));
        starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(dotsView, DotsView.DOTS_PROGRESS, 0, 1f);
        dotsAnimator.setDuration((long) (900 / animationSpeed));
        dotsAnimator.setStartDelay((long) (50 / animationSpeed));
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
                circleView.setInnerCircleRadiusProgress(0);
                circleView.setOuterCircleRadiusProgress(0);
                dotsView.setCurrentProgress(0);
                imageView.setScaleX(1);
                imageView.setScaleY(1);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onEventAnimationEnd(imageView,isChecked);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onEventAnimationStart(imageView,isChecked);
                }
            }
        });

        animatorSet.start();
    }

    public @Px int getImageSize() {
        return imageSize;
    }

    public void setImageSize(@Px int imageSize) {
        this.imageSize = imageSize;
    }

    public @ColorInt int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(@ColorInt int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public @ColorInt int getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(@ColorInt int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setAnimationSpeed(float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    /**
     * @return Returns whether the button is checked (Active) or not.
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Change Button State (Works only if both active and disabled image resource is defined)
     *
     * @param flag desired checked state of the button
     */
    public void setChecked(boolean flag) {
        isChecked = flag;
        imageView.setImageResource(isChecked ? imageResourceIdActive : imageResourceIdInactive);
        imageView.setColorFilter(isChecked ? activeImageTint : inActiveImageTint, PorterDuff.Mode.SRC_ATOP);
    }

    public void setInactiveImage(int inactiveResource){
        this.imageResourceIdInactive = inactiveResource;
        imageView.setImageResource(isChecked ? imageResourceIdActive : imageResourceIdInactive);
    }

    public void setActiveImage(int activeResource){
        this.imageResourceIdActive = activeResource;
        imageView.setImageResource(isChecked ? imageResourceIdActive : imageResourceIdInactive);
    }

    public void setEventListener(SparkEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (imageResourceIdInactive != INVALID_RESOURCE_ID) {
            isChecked = !isChecked;

            imageView.setImageResource(isChecked ? imageResourceIdActive : imageResourceIdInactive);
            imageView.setColorFilter(isChecked ? activeImageTint : inActiveImageTint, PorterDuff.Mode.SRC_ATOP);

            if (animatorSet != null) {
                animatorSet.cancel();
            }
            if (isChecked) {
                circleView.setVisibility(View.VISIBLE);
                dotsView.setVisibility(VISIBLE);
                playAnimation();
            } else {
                dotsView.setVisibility(INVISIBLE);
                circleView.setVisibility(View.GONE);
            }
        } else {
            playAnimation();
        }
        if (listener != null) {
            listener.onEvent(imageView, isChecked);
        }
    }

    private void setOnTouchListener() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        imageView.animate().scaleX(0.8f).scaleY(0.8f).setDuration(150).setInterpolator(DECELERATE_INTERPOLATOR);
                        setPressed(true);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                        imageView.animate().scaleX(1).scaleY(1).setInterpolator(DECELERATE_INTERPOLATOR);
                        if (isPressed()) {
                            performClick();
                            setPressed(false);
                        }
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        imageView.animate().scaleX(1).scaleY(1).setInterpolator(DECELERATE_INTERPOLATOR);
                        break;
                }
                return true;
            }
        });
    }

    private void initFromXML(AttributeSet attr) {
        TypedArray a = getContext().obtainStyledAttributes(attr, R.styleable.SparkButton);
        imageSize = a.getDimensionPixelOffset(R.styleable.SparkButton_sparkbutton_iconSize, Utils.dpToPx(getContext(), 50));
        imageResourceIdActive = a.getResourceId(R.styleable.SparkButton_sparkbutton_activeImage, INVALID_RESOURCE_ID);
        imageResourceIdInactive = a.getResourceId(R.styleable.SparkButton_sparkbutton_inActiveImage, INVALID_RESOURCE_ID);
        primaryColor = ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.SparkButton_sparkbutton_primaryColor, R.color.spark_primary_color));
        secondaryColor = ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.SparkButton_sparkbutton_secondaryColor, R.color.spark_secondary_color));
        activeImageTint = ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.SparkButton_sparkbutton_activeImageTint, R.color.spark_image_tint));
        inActiveImageTint = ContextCompat.getColor(getContext(), a.getResourceId(R.styleable.SparkButton_sparkbutton_inActiveImageTint, R.color.spark_image_tint));
        animationSpeed = a.getFloat(R.styleable.SparkButton_sparkbutton_animationSpeed, 1);
        // recycle typedArray
        a.recycle();
    }
}
