package ie.toxodev.thermokingtask

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.*
import ie.toxodev.thermokingtask.testUtils.BaseUnitTest
import ie.toxodev.thermokingtask.views.front.ViewModelFront
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.junit.Test

class ViewModelFrontShould : BaseUnitTest() {

    private val viewModel: ViewModelFront
    private var mckdRepository: RoomRepository = mock()
    private var mckdScope: CoroutineScope = mock()
    private var mckdDispatcher: CoroutineDispatcher = mock()

    init {
        this.viewModel = ViewModelFront(mckdRepository)
    }

    @Test
    fun initialize_repository() {
        this.viewModel.initializeRepository(mckdScope, mckdDispatcher)
        verify(mckdRepository, times(1)).setupRepository(
            mckdScope, mckdDispatcher
        )
    }

    @Test
    fun save_vehicle_data() {
        val vehicleDataResponse = VehicleDataResponse().apply {
            this.vehicleDetails = mutableListOf(VehicleDetail("1232"), VehicleDetail("123123"))
            this.requestDetails = listOf(RequestDetail())
            this.userDetails = listOf(UserDetail())
        }
        val fixedVehicleData = FixedVehicleData().apply {
            this.setData(vehicleDataResponse)
        }
        this.viewModel.saveVehicleData(fixedVehicleData)
        verify(mckdRepository, times(1)).saveVehicleData(fixedVehicleData)
    }

    @Test
    fun get_saving_result() {
        this.viewModel.getSaveResult()
        verify(mckdRepository, times(1)).lvdVehicleInsertResponse
    }


    // ====================== //
    @Test
    fun retrieve_saved_vehicle_data_from_room_database() {
        this.viewModel.retrieveSavedVehicles()
        verify(mckdRepository, times(1)).fetchSavedVehicles()
    }

    @Test
    fun get_saved_vehicles_result() {
        this.viewModel.getSavedVehicles()
        verify(mckdRepository, times(1)).lvdSavedVehicles
    }
}