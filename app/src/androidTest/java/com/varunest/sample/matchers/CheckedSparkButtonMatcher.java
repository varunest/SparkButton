package com.varunest.sample.matchers;

import android.view.View;

import com.varunest.sparkbutton.SparkButton;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Created by skaggsm on 1/26/17.
 */

public class CheckedSparkButtonMatcher extends BaseMatcher<View> {

    public static CheckedSparkButtonMatcher isCheckedSparkButton() {
        return new CheckedSparkButtonMatcher();
    }

    @Override
    public boolean matches(Object item) {
        return item instanceof SparkButton &&
                ((SparkButton) item).isChecked();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is a checked SparkButton");
    }
}
