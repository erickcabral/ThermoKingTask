package ie.toxodev.thermokingtask.supportClasses.binderModels

import android.view.View
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.VehicleDetail

class VehicleInfoAdapterBinderModel(
    val vehicleList: List<VehicleDetail>,
    val clickListener: ICardVehicleDetailListener
) {

    interface ICardVehicleDetailListener {
        fun onVehicleCardClicked(view: View)
    }
}