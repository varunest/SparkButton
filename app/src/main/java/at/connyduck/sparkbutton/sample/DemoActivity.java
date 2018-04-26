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

package at.connyduck.sparkbutton.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import at.connyduck.sparkbutton.SparkButton;

public class DemoActivity extends AppCompatActivity {
    private ViewPager showcaseViewpager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initWidgets();
        initAnimation();
    }

    private void initAnimation() {
        showcaseViewpager.postDelayed(new Runnable() {
            @Override
            public void run() {
                View starLayout = getViewFromPosition(0);
                if (starLayout != null) {
                    playStarAnimation(starLayout);
                }
            }
        }, 500);
    }

    private void initWidgets() {
        showcaseViewpager = findViewById(R.id.showcase_viewpager);
        pagerAdapter = new ScreenSlidePagerAdapter(DemoActivity.this);
        showcaseViewpager.setAdapter(pagerAdapter);
        showcaseViewpager.addOnPageChangeListener(getOnPageChangeListener());
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(showcaseViewpager);
    }

    @NonNull
    private ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View view = getViewFromPosition(position);
                if (view != null) {
                    switch (position) {
                        case 0:
                            playStarAnimation(view);
                            break;
                        case 1:
                            playHeartAnimation(view);
                            break;
                        case 2:
                            playFacebookAnimation(view);
                            break;
                        case 3:
                            playTwitterAnimation(view);
                            break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    private View getViewFromPosition(int position) {
        View view = null;
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            view = showcaseViewpager.findViewWithTag(String.valueOf(position));
            if (view != null) {
                break;
            }
        }
        return view;
    }

    private void playHeartAnimation(final View heartLayout) {
        ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).setChecked(true);
                ((SparkButton) heartLayout.findViewById(R.id.heart_button)).playAnimation();
            }
        }, 300);
    }

    private void playStarAnimation(final View starLayout) {
        ((SparkButton) starLayout.findViewById(R.id.star_button1)).setChecked(false);
        ((SparkButton) starLayout.findViewById(R.id.star_button2)).setChecked(true);
        ((SparkButton) starLayout.findViewById(R.id.star_button2)).playAnimation();
    }

    private void playFacebookAnimation(View facebookLayout) {
        ((SparkButton) facebookLayout.findViewById(R.id.facebook_button)).playAnimation();
    }

    private void playTwitterAnimation(View twitterLayout) {
        ((SparkButton) twitterLayout.findViewById(R.id.twitter_button)).playAnimation();
    }
}
