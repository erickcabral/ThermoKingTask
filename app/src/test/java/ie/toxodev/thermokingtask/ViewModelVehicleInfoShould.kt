package ie.toxodev.thermokingtask

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail
import ie.toxodev.thermokingtask.testUtils.BaseUnitTest
import ie.toxodev.thermokingtask.views.vehicleInfo.ViewModelVehicleInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.junit.Assert.*
import org.junit.Test

class ViewModelVehicleInfoShould : BaseUnitTest() {

    private val viewModel: ViewModelVehicleInfo
    private val mckdRepository: RoomRepository = mock()
    private val mckdScope = mock<CoroutineScope>()
    private val mckdDispatcher = mock<CoroutineDispatcher>()


    private val vehicleData = FixedVehicleData().apply {
        sensors = mutableListOf(
            SensorDetail(serialNumber = "ABCDEF"),
            SensorDetail(serialNumber = "REWQ"),
            SensorDetail(serialNumber = "POIUYT")
        )
    }

    init {
        this.viewModel = ViewModelVehicleInfo( mckdRepository)
    }

    @Test
    fun setup_repository() {
        viewModel.setupRepository(mckdScope, mckdDispatcher)
        verify(this.mckdRepository, times(1)).setupRepository(mckdScope, mckdDispatcher)
    }

    @Test
    fun fetch_vehicle_data_from_room_database() {
        val vehicleId = "123123"
        viewModel.fetchVehicleInfo(vehicleId)
        verify(mckdRepository, times(1)).fetchVehicleInfo(vehicleId)
    }

    @Test
    fun get_vehicle_data_response() {
        viewModel.getVehicleInfoResponse()
        verify(mckdRepository, times(1)).lvdVehicleInfo
    }

    @Test
    fun update_vehicle_info_live_data() {
        assertNull(viewModel.lvdVehicleInfo.value)
        viewModel.lvdVehicleInfo.value = vehicleData
        assertNotNull(viewModel.lvdVehicleInfo.value)
    }

    @Test
    fun delete_sensor_and_update_live_data() {
        viewModel.lvdVehicleInfo.value = vehicleData
        val sensorSerial = "ABCDEF"
        viewModel.deleteSensor(sensorSerial)
        assertNotEquals(3, viewModel.lvdVehicleInfo.value!!.sensors.size)
    }

    @Test
    fun update_current_vehicle_info_in_room(){
        this.vehicleData
        viewModel.updateVehicle(this.vehicleData)
        verify(mckdRepository, times(1)).updateVehicleInfo(this.vehicleData)
    }
}