package com.example.englishpremierleague.main

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.englishpremierleague.R
import com.example.englishpremierleague.base.BaseUITest
import com.example.englishpremierleague.di.generateTestAppComponent
import com.example.englishpremierleague.helpers.recyclerItemAtPosition
import com.example.englishpremierleague.presentation.main.adapter.MatchesViewHolder
import com.example.englishpremierleague.presentation.main.view.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUITest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun start() {
        super.setUp()
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun test_recyclerview_elements_for_expected_response() {
        mActivityTestRule.launchActivity(null)

        mockNetworkResponseWithFileContent("matches.json", HttpURLConnection.HTTP_OK)

        SystemClock.sleep(1000)

        onView(withId(R.id.recycler_view_matches))
            .check(
                matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(withText("Brentford FC"))
                    )
                )
            )

        onView(withId(R.id.recycler_view_matches))
            .check(
                matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(withText("Arsenal FC"))
                    )
                )
            )

        onView(withId(R.id.recycler_view_matches)).perform(
            RecyclerViewActions.scrollToPosition<MatchesViewHolder>(
                9
            )
        )

        onView(withId(R.id.recycler_view_matches)).check(
            matches(
                recyclerItemAtPosition(
                    2,
                    ViewMatchers.hasDescendant(withText("Watford FC"))
                )
            )
        )

        onView(withId(R.id.recycler_view_matches)).check(
            matches(
                recyclerItemAtPosition(
                    2,
                    ViewMatchers.hasDescendant(withText("Aston Villa FC"))
                )
            )
        )

    }
}