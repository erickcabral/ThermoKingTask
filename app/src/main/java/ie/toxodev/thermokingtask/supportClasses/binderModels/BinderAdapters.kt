package ie.toxodev.thermokingtask.supportClasses.binderModels

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.thermokingtask.supportClasses.SensorTypeSelector
import ie.toxodev.thermokingtask.supportClasses.recyclerAdapters.AdapterSensor
import ie.toxodev.thermokingtask.supportClasses.recyclerAdapters.AdapterVehicleInfo

@BindingAdapter("bind:recycler_vehicle_info")
fun loadVehicleInfoRecycler(
    recyclerView: RecyclerView,
    vehicleInfoAdapterBinderModel: VehicleInfoAdapterBinderModel?
) {
    vehicleInfoAdapterBinderModel?.let { adapterModel ->
        AdapterVehicleInfo(adapterModel).run {
            recyclerView.adapter = this
            this.notifyDataSetChanged()
        }

    }
}
@BindingAdapter("bind:sensor_type_selector")
fun sensorTypeSelector(
    textView: TextView,
    sensorType: Int?
) {
    sensorType?.let {
        val sensorType:String = SensorTypeSelector.values()[it-1].name
        textView.text = sensorType
    }
}

@BindingAdapter("bind:recycler_sensors")
fun loadSensorsRecycler(
    recyclerView: RecyclerView,
    sensorsAdapterModel: SensorsAdapterModel?
) {
    sensorsAdapterModel?.let { adapterModel ->
        AdapterSensor(adapterModel).run {
            recyclerView.adapter = this
            this.notifyDataSetChanged()
        }

    }
}