package com.varunest.sample.matchers;

import android.view.View;

import com.varunest.sparkbutton.SparkButton;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Created by skaggsm on 1/26/17.
 */

public class UncheckedSparkButtonMatcher extends BaseMatcher<View> {
    public static UncheckedSparkButtonMatcher isUncheckedSparkButton() {
        return new UncheckedSparkButtonMatcher();
    }

    @Override
    public boolean matches(Object item) {
        return item instanceof SparkButton &&
                !((SparkButton) item).isChecked();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is an unchecked SparkButton");
    }
}
