package com.capotter.urbandictionary

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.capotter.urbandictionary.view.MainActivity

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class MainViewModelInstrumentedTest {

    /**
     * Define Activity to be tested
     */
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * Verify whether information successfully displays after search attempt
     */
    @Test
    fun canDefineUserTerm(){
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.replaceText("word"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.search_button))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.indeterminateProgressBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.definitionRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}