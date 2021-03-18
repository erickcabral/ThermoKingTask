package ie.toxodev.thermokingtask.views.front

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.toxodev.thermokingtask.supportClasses.binderModels.VehicleInfoAdapterBinderModel
import ie.toxodev.thermokingtask.supportClasses.repository.RoomRepository
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ViewModelFront @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    val lvdAdapterBinderModel: MutableLiveData<VehicleInfoAdapterBinderModel> = MutableLiveData()

    companion object {
        const val TAG = "<<_VM_FRONT_>>"
    }

    init {
        this.initializeRepository(viewModelScope, Dispatchers.IO)
    }

    fun initializeRepository(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        this.repository.setupRepository(scope, dispatcher)
    }

    fun saveVehicleData(fixedVehicleData: FixedVehicleData) {
        this.repository.saveVehicleData(fixedVehicleData)
    }

    fun retrieveSavedVehicles() {
        this.repository.fetchSavedVehicles()
    }

    // ======================== GETTERS ================== //
    fun getSaveResult() = this.repository.lvdVehicleInsertResponse
    fun getSavedVehicles() = this.repository.lvdSavedVehicles
}