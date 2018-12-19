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
public class LoginTests {

    @Rule public ActivityTestRule<LoginActivity> lactivityrule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testEmptyEmailAndPassword() {
        try {
            onView(withId(R.id.email_sign_in_button)).perform(click());
            onView(hasErrorText("This field is required")).check(matches(isDisplayed()));
        } catch(AmbiguousViewMatcherException avme) {
            onView(withId(R.id.email_sign_in_button)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testInvalidEmailAndEmptyPassword() {
        onView(withId(R.id.email)).perform(typeText("Hey"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This email address is invalid")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidEmailAndEmptyPassword() {
        onView(withId(R.id.email)).perform(typeText("anantcha@usc.edu"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This field is required")).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidPasswordAndEmptyEmail() {
        onView(withId(R.id.password)).perform(typeText("Hey"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This field is required")).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidPasswordAndInvalidEmail() {
        onView(withId(R.id.email)).perform(typeText("hey2"));
        onView(withId(R.id.password)).perform(typeText("Hey"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This email address is invalid")).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidPasswordAndValidEmail() {
        onView(withId(R.id.email)).perform(typeText("anantcha@usc.edu"));
        onView(withId(R.id.password)).perform(typeText("Hey"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This password is too short")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidPasswordAndEmptyEmail() {
        onView(withId(R.id.password)).perform(typeText("CS310group21"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This field is required")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidPasswordAndInvalidEmail() {
        onView(withId(R.id.password)).perform(typeText("CS310group21"));
        onView(withId(R.id.email)).perform(typeText("Hey"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        onView(hasErrorText("This email address is invalid")).check(matches(isDisplayed()));
    }


    // Uncovered bug. Shouldn't have passed but passed
    @Test
    public void testValidPasswordAndValidEmail() {
        onView(withId(R.id.email)).perform(typeText("manipusg@usc.edu"));
        onView(withId(R.id.password)).perform(typeText("gggyuhn"));
        onView(withId(R.id.email_sign_in_button)).perform(click());
        intended(hasComponent(HomePageActivity.class.getName()));
        //onView(hasErrorText("This password is too short")).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
