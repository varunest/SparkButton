package com.varunest.sample;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.varunest.sample.sparkbutton.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.varunest.sample.matchers.CheckedSparkButtonMatcher.isCheckedSparkButton;
import static com.varunest.sample.matchers.UncheckedSparkButtonMatcher.isUncheckedSparkButton;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DemoActivityTest {

    @Rule
    public ActivityTestRule<DemoActivity> mActivityTestRule = new ActivityTestRule<>(DemoActivity.class);

    @Test
    public void demoActivityTest() {

        SystemClock.sleep(1000); // Wait for buttons to be checked by listener's animation

        ViewInteraction starButton1 = onView(allOf(
                withId(R.id.star_button1),
                isDisplayed()));
        starButton1.check(matches(isUncheckedSparkButton()));

        ViewInteraction starButton2 = onView(allOf(
                withId(R.id.star_button2),
                isDisplayed()));
        starButton2.check(matches(isCheckedSparkButton()));

        starButton1.perform(click());
        starButton1.check(matches(isCheckedSparkButton()));

        starButton1.perform(swipeRight()); // Make sure that swiping doesn't check/uncheck the button
        starButton1.check(matches(isCheckedSparkButton()));

        ViewInteraction appCompatTextView = onView(allOf(
                withText("Heart"),
                isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction heartButton = onView(allOf(
                withId(R.id.heart_button),
                isDisplayed()));
        SystemClock.sleep(1000); // Wait for buttons to be checked by listener's animation
        heartButton.check(matches(isCheckedSparkButton()));

        heartButton.perform(longClick());
        heartButton.check(matches(isUncheckedSparkButton()));

        ViewInteraction heartRoot = onView(allOf(
                withId(R.id.background),
                isDisplayed()));
        heartRoot.perform(swipeLeft());

        ViewInteraction facebookButton = onView(allOf(
                withId(R.id.facebook_button),
                isDisplayed()));
        SystemClock.sleep(1000); // Wait for buttons to be checked by listener's animation
        facebookButton.check(matches(isUncheckedSparkButton()));
        facebookButton.perform(longClick());
        facebookButton.check(matches(isUncheckedSparkButton())); //Facebook button is not checkable

        ViewInteraction facebookRoot = onView(allOf(
                withId(R.id.background),
                isDisplayed()));
        facebookRoot.perform(swipeLeft());

        ViewInteraction twitterButton = onView(allOf(
                withId(R.id.twitter_button),
                isDisplayed()));
        SystemClock.sleep(1000); // Wait for buttons to be checked by listener's animation
        twitterButton.check(matches(isUncheckedSparkButton()));
    }
}
