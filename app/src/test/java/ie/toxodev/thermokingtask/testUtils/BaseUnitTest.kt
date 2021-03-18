package ie.toxodev.thermokingtask.testUtils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.outsideintddexample.acceptancetests.MainCoroutineScopeRule
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseUnitTest {


    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Test
    fun notNull(){
        assertNotNull(mainCoroutineScopeRule)
    }
}