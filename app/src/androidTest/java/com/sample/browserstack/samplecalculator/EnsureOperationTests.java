package com.sample.browserstack.samplecalculator;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Espresso tests to ensure that simple operations result in
 * correct output when the number & operations buttons are clicked
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class EnsureOperationTests {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = activityRule.getActivity();
    }

    void childScreenshotMethod(String screenshotName) {
        ScreenshotUtils screenshotUtils = new ScreenshotUtils();
        screenshotUtils.captureScreenshot(screenshotName);
    }

    void parentScreenshotMethod(String screenshotName) {
        childScreenshotMethod(screenshotName);
    }

    private boolean checkScreenshotFile(String screenshotName, String testName) {
        Log.d("Screenshot", "Entering");
        File baseFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)), "screenshots");
//        File baseFile = new File(String.valueOf(ApplicationProvider.getApplicationContext().getExternalFilesDir(null)), "screenshots");
        File classDir = new File(baseFile, "com.sample.browserstack.samplecalculator.EnsureOperationTests");
        File testDir = new File(classDir, testName);
        File[] fileList = testDir.listFiles();
        if(fileList == null) return false;
        for (int i = 0; i < fileList.length; i++) {
            Log.d("Screenshot", "Filename: " + fileList[i].getAbsolutePath());
            if(fileList[i].getName().endsWith(screenshotName + ".png")) return true;
        }
        return false;
    }

    @Test
    public void ensureAdditionWorks() {
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        onView(withId(R.id.buttonAdd)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonEqual)).perform(click());
        onView(withId(R.id.editText)).check(matches(withText("33")));

        ScreenshotUtils screenshotUtils = new ScreenshotUtils();
        screenshotUtils.captureScreenshot("post_addition");

        boolean result = checkScreenshotFile("post_addition", "ensureAdditionWorks");
        Assert.assertEquals(true, result);

    }

    @Test
    public void ensureSubtractionWorks() {
        ScreenshotUtils screenshotUtils = new ScreenshotUtils();
        screenshotUtils.captureScreenshot("pre_subtraction");

        onView(withId(R.id.buttonTwo)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        onView(withId(R.id.buttonSubtract)).perform(click());
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonEqual)).perform(click());
        onView(withId(R.id.editText)).check(matches(withText("11")));

        screenshotUtils.captureScreenshot("post_subtraction");

        boolean result = checkScreenshotFile("post_subtraction", "ensureSubtractionWorks");
        Assert.assertEquals(true, result);
    }

    @Test
    public void ensureMultiplicationWorks() {
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        onView(withId(R.id.buttonMultiply)).perform(click());
        onView(withId(R.id.buttonFive)).perform(click());
        onView(withId(R.id.buttonEqual)).perform(click());
        onView(withId(R.id.editText)).check(matches(withText("60")));

        childScreenshotMethod("post_multiplication");

        boolean result = checkScreenshotFile("post_multiplication", "ensureMultiplicationWorks");
        Assert.assertEquals(true, result);
    }

    @Test
    public void ensureDivisionWorks() {
        onView(withId(R.id.buttonOne)).perform(click());
        onView(withId(R.id.buttonTwo)).perform(click());
        onView(withId(R.id.buttonDivide)).perform(click());
        onView(withId(R.id.buttonThree)).perform(click());
        onView(withId(R.id.buttonEqual)).perform(click());
        onView(withId(R.id.editText)).check(matches(withText("4")));

        parentScreenshotMethod("post_division");

        boolean result = checkScreenshotFile("post_division", "ensureDivisionWorks");
        Assert.assertEquals(true, result);
    }
}
