package ie.toxodev.thermokingtask

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import getValueForTest
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail
import ie.toxodev.thermokingtask.testUtils.BaseUnitTest
import ie.toxodev.thermokingtask.views.viewSensorEditor.ViewModelSensorEditor
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ViewSensorEditorShould : BaseUnitTest() {

    private val viewModel: ViewModelSensorEditor
    private val mckdRepository: RoomRepository = mock()
    private val mckdScope = mock<CoroutineScope>()
    private val mckdDispatcher = mock<CoroutineDispatcher>()

    init {
        this.viewModel = ViewModelSensorEditor(mckdRepository)
    }

    @Test
    fun setup_repository() {
        viewModel.setupRepository(mckdScope, mckdDispatcher)
        verify(mckdRepository, times(1)).setupRepository(mckdScope, mckdDispatcher)
    }

    @Test
    fun retrieve_vehicle_info() {
        val vehicleId = "11224455"
        this.viewModel.retrieveVehicleInfo(vehicleId)
        verify(mckdRepository, times(1)).fetchVehicleInfo(vehicleId)
    }

    @Test
    fun get_vehicle_info_response() {
        this.viewModel.getVehicleInfoResponse()
        verify(mckdRepository, times(1)).lvdVehicleInfo
    }

    @Test
    fun set_sensor_detail_live_data() = runBlocking{
        val sensorSerial = "X000A3312T"
        FixedVehicleData().apply {
            this.sensors = mutableListOf(SensorDetail(serialNumber = sensorSerial))
        }.also {
            viewModel.lvdVehicleInfo.value = it
        }
        viewModel.setSensorDetail(sensorSerial)
        viewModel.lvdSensorDetail.getValueForTest().run {
            assertNotNull(this)
            assertEquals(sensorSerial, this!!.serialNumber)
        }
    }
}