package ie.toxodev.thermokingtask.androidTestUtils

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ie.toxodev.thermokingtask.activities.MainActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseAndroidTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val context = InstrumentationRegistry.getInstrumentation().targetContext

}