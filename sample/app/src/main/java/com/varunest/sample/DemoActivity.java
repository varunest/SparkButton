package com.varunest.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.varunest.sparkbutton.SparkButton;

public class DemoActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private FrameLayout sparkButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initWidgets();
        inflateStarLayout();
    }

    private void initWidgets() {
        radioGroup = (RadioGroup) findViewById(R.id.search_type_radio_group);
        radioGroup.setOnCheckedChangeListener(getCheckedChangeListener());
        sparkButtonContainer = (FrameLayout) findViewById(R.id.sparkbutton_container);
    }

    private void inflateHeartLayout() {
        sparkButtonContainer.removeAllViews();
        final View heartLayout = LayoutInflater.from(DemoActivity.this).inflate(R.layout.demo_heart, sparkButtonContainer, false);
        sparkButtonContainer.addView(heartLayout);
        ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(true);
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).playAnimation();
            }
        }, 300);
    }

    private void inflateStarLayout() {
        sparkButtonContainer.removeAllViews();
        final View starLayout = LayoutInflater.from(DemoActivity.this).inflate(R.layout.demo_star, sparkButtonContainer, false);
        sparkButtonContainer.addView(starLayout);
        ((SparkButton) starLayout.findViewById(R.id.star_button1)).setChecked(false);
        ((SparkButton) starLayout.findViewById(R.id.star_button2)).setChecked(false);
        starLayout.findViewById(R.id.cardview_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starLayout.findViewById(R.id.star_button1).performClick();
            }
        });
        starLayout.findViewById(R.id.cardview_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starLayout.findViewById(R.id.star_button2).performClick();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SparkButton) starLayout.findViewById(R.id.star_button2)).setChecked(true);
                ((SparkButton) starLayout.findViewById(R.id.star_button2)).playAnimation();
            }
        }, 500);
    }

    private void inflateFacebookLayout() {
        sparkButtonContainer.removeAllViews();
        View facebookLayout = LayoutInflater.from(DemoActivity.this).inflate(R.layout.demo_facebook, sparkButtonContainer, false);
        sparkButtonContainer.addView(facebookLayout);
        ((SparkButton) facebookLayout.findViewById(R.id.facebook_button)).playAnimation();
    }

    private void inflateTWitterLayout() {
        sparkButtonContainer.removeAllViews();
        View twitterLayout = LayoutInflater.from(DemoActivity.this).inflate(R.layout.demo_twitter, sparkButtonContainer, false);
        sparkButtonContainer.addView(twitterLayout);
        ((SparkButton)twitterLayout.findViewById(R.id.twitter_button)).playAnimation();
    }

    @NonNull
    private RadioGroup.OnCheckedChangeListener getCheckedChangeListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sparkButtonContainer.removeAllViews();
                switch (checkedId) {
                    case R.id.star:
                        inflateStarLayout();
                        break;

                    case R.id.heart:
                        inflateHeartLayout();
                        break;

                    case R.id.facebook:
                        inflateFacebookLayout();
                        break;

                    case R.id.twitter:
                        inflateTWitterLayout();
                        break;
                }
            }
        };
    }
}
