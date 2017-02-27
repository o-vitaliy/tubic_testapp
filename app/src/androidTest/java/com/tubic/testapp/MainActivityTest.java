package com.tubic.testapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ovitaliy on 27.02.2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSwipes() {

        onView(withId(R.id.main_viewpager)).check(matches(hasDescendant(withId(R.id.google_main))));
        onView(withId(R.id.main_viewpager)).perform(swipeLeft()).check(matches(hasDescendant(withId(R.id.facebook_main))));
        onView(withId(R.id.main_viewpager)).perform(swipeLeft()).check(matches(hasDescendant(withId(R.id.favorites_main))));
        //one more swipe to check view pager bounds
        onView(withId(R.id.main_viewpager)).perform(swipeLeft()).check(matches(hasDescendant(withId(R.id.favorites_main))));
        //swipe back
        onView(withId(R.id.main_viewpager)).perform(swipeRight()).check(matches(hasDescendant(withId(R.id.facebook_main))));
        onView(withId(R.id.main_viewpager)).perform(swipeRight()).check(matches(hasDescendant(withId(R.id.google_main))));
    }


}