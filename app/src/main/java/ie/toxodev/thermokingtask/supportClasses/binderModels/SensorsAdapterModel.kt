package ie.toxodev.thermokingtask.supportClasses.binderModels

import android.view.View
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail

class SensorsAdapterModel(
    val sensorList: List<SensorDetail>,
    val clickListener: ISensorAdapterClickListener
) {

    interface ISensorAdapterClickListener {
        fun onCardClicked(view: View)
        fun onDeleteIconClicked(view: View)
        fun onEditIconClicked(view: View)
    }
}