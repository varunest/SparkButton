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

package at.connyduck.sparkbutton.sample.matchers;

import android.view.View;

import at.connyduck.sparkbutton.SparkButton;

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
