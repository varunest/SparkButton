package com.varunest.sample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varunest.sample.sparkbutton.R;
import com.varunest.sparkbutton.SparkButton;

/**
 * @author varun on 08/07/16.
 */
public class ScreenSlidePagerAdapter extends PagerAdapter {
    private Context context;

    public ScreenSlidePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
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
        }
        if (view != null) {
            view.setTag(String.valueOf(position));
            container.addView(view);
        }
        return view;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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

    public void setupStarLayoutClickEvents(final View view) {
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

    public void setupFacebookLayoutClickEvents(View view) {
        view.findViewById(R.id.github_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubPage();
            }
        });
    }

    public void setupTwitterLayoutClickEvents(final View view) {
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
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/varunest"));
        context.startActivity(browserIntent);
    }

    private void openTwitterPage() {
        Intent intent = null;
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=930431515"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/930431515"));
        }
        context.startActivity(intent);
    }
}
