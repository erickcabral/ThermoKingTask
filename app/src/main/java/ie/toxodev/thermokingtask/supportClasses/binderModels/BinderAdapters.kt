package ie.toxodev.thermokingtask.supportClasses.binderModels

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import ie.toxodev.thermokingtask.R
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
        val sensorType: String = SensorTypeSelector.values()[it - 1].name
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

@BindingAdapter("bind:spinner_loader")
fun spinnerLoader(inputLayout: TextInputLayout, sensorType: Int?) {
    if (sensorType != null && sensorType != 0) {
        SensorTypeSelector.values()[(sensorType - 1)].name.run {
            inputLayout.editText?.setText(this)
            SensorTypeSelector.values().also { sensorList ->
                ArrayAdapter(
                    inputLayout.context,
                    R.layout.cont_text_list_item,
                    sensorList
                ).also { adapter ->
                    (inputLayout.editText as? AutoCompleteTextView)?.run {
                        this.setAdapter(adapter)
                    }
                }
            }
        }
    }
}

@BindingAdapter("bind:sensor_type")
fun sensorTypeSelector(inputLayout: TextInputLayout, sensorType: Int?) {
    if (sensorType != null && sensorType != 0) {
        SensorTypeSelector.values()[(sensorType - 1)].name.run {
            inputLayout.editText?.setText(this)
        }
    }
}
