package ie.toxodev.thermokingtask.supportClasses.repository

import androidx.lifecycle.MutableLiveData
import ie.toxodev.thermokingtask.supportClasses.room.VehicleDatabase
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.VehicleDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject


class RoomRepository @Inject constructor(
    private val database: VehicleDatabase
) {
    var job: CoroutineScope? = null

    val lvdVehicleInfo: MutableLiveData<FixedVehicleData> = MutableLiveData()
    val lvdVehicleInsertResponse: MutableLiveData<Boolean> = MutableLiveData()
    val lvdSavedVehicles: MutableLiveData<List<VehicleDetail>> = MutableLiveData()

    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.job = scope.plus(dispatcher)
    }

    fun saveVehicleData(vehicleDataResponse: FixedVehicleData) {
        this.job?.launch {
            database.getDao().insert(vehicleDataResponse).run {
                val success: Boolean = (this != 0L)
                lvdVehicleInsertResponse.postValue(success)
            }
        }
    }

    fun fetchSavedVehicles() {
        this.job?.launch {
            database.getDao().fetchSavedVehicles().run {
                val vehicleDetailList = mutableListOf<VehicleDetail>()
                this.forEach { model ->
                    model.vehicleDetail?.let {
                        vehicleDetailList.add(it)
                    }
                }
                lvdSavedVehicles.postValue(vehicleDetailList)
            }
        }
    }

    fun fetchVehicleInfo(vehicleId: String) {
       this.job?.launch {
           database.getDao().fetchVehicleInfo(vehicleId.toLong()).run {
               lvdVehicleInfo.postValue(this)
           }
       }
    }

    fun updateVehicleInfo(vehicleData: FixedVehicleData) {
        this.job?.launch {
            database.getDao().updateVehicleInfo(vehicleData).run {
                val success = this == 1
                lvdVehicleInsertResponse.postValue(success)
            }
        }
    }
}