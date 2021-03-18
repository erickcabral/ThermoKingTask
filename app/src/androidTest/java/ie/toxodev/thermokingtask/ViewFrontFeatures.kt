package ie.toxodev.thermokingtask

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import ie.toxodev.thermokingtask.androidTestUtils.BaseAndroidTest
import ie.toxodev.thermokingtask.supportClasses.recyclerAdapters.AdapterVehicleInfo
import org.junit.Before
import org.junit.Test

class ViewFrontFeatures : BaseAndroidTest() {

    @Before
    fun setup() {
        activityRule.scenario.onActivity {
            it.findNavController(R.id.navHostMain).setGraph(R.navigation.navigation_main)
        }
    }

    @Test
    fun layout_displayed() {
        assertDisplayed(R.id.bntLoadData)
        assertDisplayed(R.id.recyclerVehicleDetail)
    }

    @Test
    fun populate_recycler_view_when_data_retrieved() {
        onView(withId(R.id.bntLoadData)).perform(click())
        onView(withId(R.id.recyclerVehicleDetail)).check(
            matches(hasMinimumChildCount(1))
        )
    }

    @Test
    fun navigate_to_vehicle_details_when_vehicle_card_clicked() {
        RecyclerViewActions.actionOnItemAtPosition<AdapterVehicleInfo.ViewHolderVehicleInfo>(
            0, click()
        )

        Thread.sleep(500)
        assertDisplayed(R.id.layoutVehicleInfo)
    }

}