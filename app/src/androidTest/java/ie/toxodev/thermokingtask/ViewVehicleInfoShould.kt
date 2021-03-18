package ie.toxodev.thermokingtask

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import ie.toxodev.thermokingtask.androidTestUtils.BaseAndroidTest
import ie.toxodev.thermokingtask.views.vehicleInfo.ViewVehicleInfo
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class ViewVehicleInfoShould : BaseAndroidTest() {
    @Before
    fun setup() {
        activityRule.scenario.onActivity {
            it.findNavController(R.id.navHostMain)
                .navigate(R.id.viewVehicleInfo, bundleOf(ViewVehicleInfo.VEHICLE_ID to "11224455"))
        }
    }

    private fun getTextViewInContainer(tag: String, @IdRes tvId: Int): ViewInteraction {
        return onView(allOf(withTagValue((`is`(matches(withText(tag)))))))
    }

    @Test
    fun layout_full_displayed() {
        assertDisplayed(R.id.layoutVehicleInfo)
        Thread.sleep(100)
        val customer = context.resources.getString(R.string.txt_customer)
        this.getTextViewInContainer(
            customer,
            R.id.tvLabel
        ).check(matches(withText(customer)))

        this.getTextViewInContainer(
            context.resources.getString(R.string.txt_vehicle_id),
            R.id.tvLabel
        )
        this.getTextViewInContainer(
            context.resources.getString(R.string.txt_mobile_num),
            R.id.tvLabel
        )
        this.getTextViewInContainer(context.resources.getString(R.string.txt_reefer), R.id.tvLabel)
        this.getTextViewInContainer(
            context.resources.getString(R.string.vehicle_name),
            R.id.tvLabel
        )

        assertDisplayed(R.id.recyclerSensors)
    }

    @Test
    fun display_text_fields_when_vehicle_retrieved() {
        this.getTextViewInContainer(
            context.resources.getString(R.string.txt_customer),
            R.id.tvInfo
        )
        this.getTextViewInContainer(
            context.resources.getString(R.string.txt_vehicle_id),
            R.id.tvInfo
        )
        this.getTextViewInContainer(
            context.resources.getString(R.string.txt_mobile_num),
            R.id.tvInfo
        )
        this.getTextViewInContainer(context.resources.getString(R.string.txt_reefer), R.id.tvLabel)
        this.getTextViewInContainer(
            context.resources.getString(R.string.vehicle_name),
            R.id.tvInfo
        )
    }

    @Test
    fun navigate_to_sensor_editor_view_when_sensor_card_clicked() {
        val sensorID = "X000A3312T"
        onView(
            allOf(
                withId(R.id.imgBtnEdit),
                withTagValue(`is`(matches(withText(sensorID))))
            )
        ).perform(
            click()
        )
        assertDisplayed(R.id.layoutSensorEditor)
    }
}