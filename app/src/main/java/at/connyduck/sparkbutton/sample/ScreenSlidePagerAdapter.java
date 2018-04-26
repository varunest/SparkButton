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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.connyduck.sparkbutton.SparkButton;

public class ScreenSlidePagerAdapter extends PagerAdapter {
    private Context context;

    ScreenSlidePagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        switch (position) {
            case 0:
                view =  LayoutInflater.from(context).inflate(R.layout.demo_star, container, false);
                setupStarLayoutClickEvents(view);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.demo_heart, container, false);
                setupHeartLayoutClickEvents(view);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.demo_facebook, container, false);
                setupFacebookLayoutClickEvents(view);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.demo_twitter, container, false);
                setupTwitterLayoutClickEvents(view);
                break;
                default:
                    throw new IllegalStateException();
        }

        view.setTag(String.valueOf(position));
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = context.getString(R.string.star);
                break;
            case 1:
                title = context.getString(R.string.heart);
                break;
            case 2:
                title = context.getString(R.string.facebook);
                break;
            case 3:
                title = context.getString(R.string.twitter);
                break;
        }
        return title;
    }

    private void setupStarLayoutClickEvents(final View view) {
        view.findViewById(R.id.cardview_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.star_button1).performClick();
            }
        });
        view.findViewById(R.id.cardview_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.star_button2).performClick();
            }
        });
        view.findViewById(R.id.github_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubPage();
            }
        });
    }

    private void setupHeartLayoutClickEvents(View view) {
        view.findViewById(R.id.github_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubPage();
            }
        });
    }

    private void setupFacebookLayoutClickEvents(View view) {
        view.findViewById(R.id.github_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubPage();
            }
        });
    }

    private void setupTwitterLayoutClickEvents(final View view) {
        view.findViewById(R.id.github_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubPage();
            }
        });
        view.findViewById(R.id.twitter_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SparkButton)view.findViewById(R.id.twitter_button)).playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openTwitterPage();

                    }
                }, 500);
            }
        });
    }

    private void openGithubPage() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/connyduck/SparkButton"));
        context.startActivity(browserIntent);
    }

    private void openTwitterPage() {
        Intent intent;
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=517550916"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ConnyDuck"));
        }
        context.startActivity(intent);
    }
}
