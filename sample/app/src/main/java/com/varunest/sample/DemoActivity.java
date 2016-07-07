package com.varunest.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;

public class DemoActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private SparkButton starButton, heartButton, facebookButton, twitterButton;
    private FrameLayout sparkButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        buildSparkButtons();
        initWidgets();
    }

    private void initWidgets() {
        radioGroup = (RadioGroup) findViewById(R.id.search_type_radio_group);
        radioGroup.setOnCheckedChangeListener(getCheckedChangeListener());
        sparkButtonContainer = (FrameLayout) findViewById(R.id.sparkbutton_container);
    }

    private void buildSparkButtons() {
        starButton = new SparkButtonBuilder(this)
                .setActiveImage(R.drawable.ic_star_on)
                .setDisabledImage(R.drawable.ic_star_off)
                .setImageSizePx(getResources().getDimensionPixelOffset(R.dimen.dimen_spark_button))
                .setPrimaryColor(getResources().getColor(R.color.spark_primary_color))
                .setSecondaryColor(getResources().getColor(R.color.spark_secondary_color))
                .build();

        heartButton = new SparkButtonBuilder(this)
                .setActiveImage(R.drawable.ic_heart_on)
                .setDisabledImage(R.drawable.ic_heart_off)
                .setImageSizePx(getResources().getDimensionPixelOffset(R.dimen.dimen_spark_button))
                .setPrimaryColor(getResources().getColor(R.color.heart_primary_color))
                .setSecondaryColor(getResources().getColor(R.color.heart_secondary_color))
                .build();

        facebookButton = new SparkButtonBuilder(this)
                .setActiveImage(R.drawable.ic_thumb)
                .setImageSizePx(getResources().getDimensionPixelOffset(R.dimen.dimen_spark_button))
                .setPrimaryColor(getResources().getColor(R.color.facebook_primary_color))
                .setSecondaryColor(getResources().getColor(R.color.facebook_secondary_color))
                .build();

        twitterButton = new SparkButtonBuilder(this)
                .setActiveImage(R.drawable.ic_twitter)
                .setImageSizePx(getResources().getDimensionPixelOffset(R.dimen.dimen_spark_button))
                .setPrimaryColor(getResources().getColor(R.color.twitter_primary_color))
                .setSecondaryColor(getResources().getColor(R.color.twitter_secondary_color))
                .build();
    }

    @NonNull
    private RadioGroup.OnCheckedChangeListener getCheckedChangeListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sparkButtonContainer.removeAllViews();
                switch (checkedId) {
                    case R.id.star:
                        sparkButtonContainer.addView(starButton);
                        starButton.setChecked(true);
                        starButton.playAnimation();
                        break;

                    case R.id.heart:
                        sparkButtonContainer.addView(heartButton);
                        heartButton.setChecked(true);
                        heartButton.playAnimation();
                        break;

                    case R.id.facebook:
                        sparkButtonContainer.addView(facebookButton);
                        facebookButton.playAnimation();
                        break;

                    case R.id.twitter:
                        sparkButtonContainer.addView(twitterButton);
                        twitterButton.playAnimation();
                        break;
                }
            }
        };
    }
}
