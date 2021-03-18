package ie.toxodev.thermokingtask

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import getValueForTest
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.room.RoomDAO
import ie.toxodev.thermokingtask.supportClasses.room.VehicleDatabase
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.VehicleDetail
import ie.toxodev.thermokingtask.testUtils.BaseUnitTest
import junit.framework.Assert.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RoomRepositoryShould : BaseUnitTest() {

    private val repository: RoomRepository

    private val mckdDatabase: VehicleDatabase = mock()
    private val mckdDao: RoomDAO = mock()
    private val mckdScope = mainCoroutineScopeRule
    private val mckdDispatcher = mainCoroutineScopeRule.dispatcher

    init {
        this.repository = RoomRepository(mckdDatabase)
        this.repository.setupRepository(mckdScope, mckdDispatcher)
        whenever(mckdDatabase.getDao()).thenReturn(mckdDao)
    }

    @Test
    fun setup_job_variable() {
        this.repository.setupRepository(mckdScope, mckdDispatcher)
        assertNotNull(this.repository.job)
    }

    @Test
    fun save_vehicle_data_on_room_data_base_and_update_live_data() = runBlockingTest {
        val fixedVehicleData = FixedVehicleData()
        whenever(mckdDatabase.getDao()).thenReturn(mckdDao)
        whenever(mckdDao.insert(fixedVehicleData)).thenReturn(1)

        repository.saveVehicleData(fixedVehicleData)
        verify(mckdDatabase.getDao(), times(1)).insert(fixedVehicleData)

        repository.lvdVehicleInsertResponse.getValueForTest().run {
            assertNotNull(this)
            assertEquals(true, this)
        }
    }

    @Test
    fun fetch_saved_vehicles_and_update_live_data() = runBlockingTest {
        whenever(mckdDatabase.getDao()).thenReturn(mckdDao)
        whenever(mckdDao.fetchSavedVehicles()).thenReturn(emptyList())
        repository.fetchSavedVehicles()
        verify(mckdDao, times(1)).fetchSavedVehicles()

        repository.lvdSavedVehicles.getValueForTest().run {
            assertNotNull(this)
        }
    }

    @Test
    fun fetch_vehicle_info_and_update_live_data() = runBlockingTest {
        val vehicleId = "123123"
        whenever(mckdDao.fetchVehicleInfo(vehicleId.toLong())).thenReturn(FixedVehicleData())
        repository.fetchVehicleInfo(vehicleId)
        verify(mckdDao, times(1)).fetchVehicleInfo(vehicleId.toLong())

        repository.lvdVehicleInfo.getValueForTest().run {
            assertNotNull(this)
        }
    }

    @Test
    fun update_vehicle_info_in_room_database() = runBlockingTest {
        val id = "322353"
        val vehicleData: FixedVehicleData =
            FixedVehicleData().apply { VehicleDetail(vehicleID = id) }
        whenever(mckdDao.updateVehicleInfo(vehicleData)).thenReturn(1)
        repository.updateVehicleInfo(vehicleData)
        verify(mckdDao, times(1)).updateVehicleInfo(vehicleData)

        repository.lvdVehicleInsertResponse.getValueForTest().run {
            assertNotNull(this)
            assertTrue(this!!)
        }
    }
}