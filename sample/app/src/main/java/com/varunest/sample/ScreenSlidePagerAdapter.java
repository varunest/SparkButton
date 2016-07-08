package com.varunest.sample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.demo_heart, container, false);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.demo_facebook, container, false);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.demo_twitter, container, false);
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
}
