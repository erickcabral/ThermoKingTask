package ie.toxodev.thermokingtask.views.vehicleInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.thermokingtask.supportClasses.binderModels.ContTextFieldBinderModel
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModelVehicleInfo @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    lateinit var contCustomer: ContTextFieldBinderModel
    lateinit var contVehicleName: ContTextFieldBinderModel
    lateinit var contVehicleId: ContTextFieldBinderModel
    lateinit var contReefer: ContTextFieldBinderModel
    lateinit var contMobile: ContTextFieldBinderModel


    val lvdVehicleInfo: MutableLiveData<FixedVehicleData> = MutableLiveData()

    companion object {
        const val TAG = "<<_TAG_ViewModelVehicleInfo_>>"
    }

    init {
        this.repository.setupRepository(viewModelScope, Dispatchers.IO)
    }

    fun setupRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        repository.setupRepository(scope, dispatcher)
    }

    fun deleteSensor(sensorSerial: String) {
        this.lvdVehicleInfo.value?.apply {
            this.sensors.forEachIndexed { index, sensorDetail ->
                if (sensorDetail.serialNumber == sensorSerial) {
                    this.sensors.removeAt(index)
                    return@apply
                }
            }
        }.run {
            this?.let {
                repository.updateVehicleInfo(this)
            }
        }
    }

    fun fetchVehicleInfo(vehicleId: String) {
        this.repository.fetchVehicleInfo(vehicleId)
    }

    fun updateVehicle(vehicleData: FixedVehicleData) {
        this.repository.updateVehicleInfo(vehicleData)
    }

    //================= GETTER ===================== //
    fun getVehicleInfoResponse() = this.repository.lvdVehicleInfo
    fun getVehicleUpdateResponse() = this.repository.lvdVehicleInsertResponse
}