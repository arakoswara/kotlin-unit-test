package com.example.kotlinSub2Ara.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.KeyEvent
import android.widget.EditText
import com.example.kotlinSub2Ara.MainActivity
import com.example.kotlinSub2Ara.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(R.id.club_list)).check(matches(isDisplayed()))
        onView(withId(R.id.club_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Thread.sleep(3000)

        onView(withId(R.id.club_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        Thread.sleep(3000)

        onView(withId(R.id.favorite)).perform(click())
        Thread.sleep(3000)

        pressBack()
    }

    @Test
    fun testSpinner() {
        onView(withId(R.id.spinner))
            .check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
    }

    @Test
    fun testSearchView() {
        onView(withId(R.id.club_list)).check(matches(isDisplayed()))
        Thread.sleep(3000)

        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Arsenal"))
        Thread.sleep(3000)

        onView(isAssignableFrom(EditText::class.java)).perform(clearText())
        Thread.sleep(3000)

        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Liverpool"))
        Thread.sleep(3000)
    }
}
