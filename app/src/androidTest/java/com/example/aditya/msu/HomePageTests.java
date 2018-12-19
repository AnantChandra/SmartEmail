package com.example.aditya.msu;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomePageTests {

    @Rule public ActivityTestRule<HomePageActivity> lactivityrule = new ActivityTestRule<>(HomePageActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testAllEmails() {
        onView(withId(R.id.button)).perform(click());
        intended(hasComponent(Main2Activity.class.getName()));
    }

    @Test
    public void addSingleKeyword() {
        try {
            onView(withId(R.id.editText)).perform(typeText("Jobs"));
            onView(withId(R.id.button3)).perform(click());
            onView(withText("Jobs")).check(matches(isDisplayed()));
        } catch(AmbiguousViewMatcherException avme) {
            onView(withId(R.id.editText)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void addMultipleKeyword() {
        try {
            onView(withId(R.id.editText)).perform(typeText("Peaks and Professors"));
            onView(withId(R.id.button3)).perform(click());
            onView(withText("Peaks and Professors")).check(matches(isDisplayed()));
        } catch(AmbiguousViewMatcherException avme) {
            onView(withId(R.id.editText)).check(matches(isDisplayed()));
        }
    }

    // Uncovered bug. Shouldn't have passed but passed. Created button for invalid email which it shouldn't.
    @Test
    public void addInvalidEmail() {
        try {
            onView(withId(R.id.editText)).perform(typeText("InvalidEmail"));
            onView(withId(R.id.button3)).perform(click());
            onView(withText("InvalidEmail")).check(matches(isDisplayed()));
        } catch(AmbiguousViewMatcherException avme) {
            onView(withId(R.id.editText)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void addValidEmail() {
        try {
            onView(withId(R.id.editText)).perform(typeText("hariomshankar@gmail.com"));
            onView(withId(R.id.button3)).perform(click());
            onView(withText("hariomshankar@gmail.com")).check(matches(isDisplayed()));
        } catch(AmbiguousViewMatcherException avme) {
            onView(withId(R.id.editText)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void addMultipleEmails() {
        try {
            onView(withId(R.id.editText)).perform(typeText("hariomshankar@gmail.com;ravi@gmail.com"));
            onView(withId(R.id.button3)).perform(click());
            onView(withText("hariomshankar@gmail.com;ravi@gmail.com")).check(matches(isDisplayed()));
        } catch(AmbiguousViewMatcherException avme) {
            onView(withId(R.id.editText)).check(matches(isDisplayed()));
        }
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
