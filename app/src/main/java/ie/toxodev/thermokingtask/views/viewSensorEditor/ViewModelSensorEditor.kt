package ie.toxodev.thermokingtask.views.viewSensorEditor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModelSensorEditor @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {

    private var sensorIndex: Int? = null

    val lvdSensorDetail: MutableLiveData<SensorDetail> = MutableLiveData()
    val lvdVehicleInfo: MutableLiveData<FixedVehicleData> = MutableLiveData()

    companion object {
        const val TAG = "<<_TAG_ViewModelSensorEditor_>>"
    }

    init {
        this.setupRepository(viewModelScope, Dispatchers.IO)
    }

    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.setupRepository(scope, dispatcher)
    }

    fun retrieveVehicleInfo(vehicleId: String) {
        this.repository.fetchVehicleInfo(vehicleId)
    }

    fun setSensorDetail(serialNumber: String) {
        lvdVehicleInfo.value?.sensors?.forEachIndexed { index, sensorDetail ->
            if (sensorDetail.serialNumber == serialNumber) {
                sensorIndex = index
                lvdSensorDetail.value = sensorDetail
                return
            }
        }
    }

    fun getVehicleInfoResponse() = repository.lvdVehicleInfo
}