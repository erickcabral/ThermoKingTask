package ie.toxodev.thermokingtask.views.viewSensorEditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.ViewSensorEditorBinding
import ie.toxodev.thermokingtask.views.vehicleInfo.ViewVehicleInfo

@AndroidEntryPoint
class ViewSensorEditor : Fragment() {
    companion object {
        const val TAG = "<<_TAG_ViewSensorEditor_>>"
        const val SENSOR_ID = "SENSOR_ID"
    }

    private lateinit var vBinder: ViewSensorEditorBinding //Layout Binder
    private val vModel: ViewModelSensorEditor by viewModels()
    private var sensorId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_sensor_editor,
            container, false
        )
        arguments?.let {
            it.getString(ViewVehicleInfo.VEHICLE_ID)?.let { vehicleId ->
                it.getString(SENSOR_ID)?.let { sensorID ->
                    this.sensorId = sensorID
                    vModel.retrieveVehicleInfo(vehicleId)
                }
            }

        }

        this.initializeLiveData()
        return this.vBinder.root
    }

    private fun initializeLiveData() {
        this.vModel.getVehicleInfoResponse().observe(viewLifecycleOwner, Observer {
            if (it != null && sensorId != null) {
                vModel.lvdVehicleInfo.value = it
                this.vModel.setSensorDetail(sensorId!!)

            } else {
                TODO("Not Implemented yet")
            }
        })

        this.vModel.lvdSensorDetail.observe(viewLifecycleOwner, Observer {
            this.vBinder.sensorDetail = it
        })
    }
}