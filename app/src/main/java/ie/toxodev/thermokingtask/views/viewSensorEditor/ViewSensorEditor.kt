package ie.toxodev.thermokingtask.views.viewSensorEditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.ViewSensorEditorBinding
import ie.toxodev.thermokingtask.supportClasses.OutputManager
import ie.toxodev.thermokingtask.supportClasses.SensorTypeSelector
import ie.toxodev.thermokingtask.views.vehicleInfo.ViewVehicleInfo

@AndroidEntryPoint
class ViewSensorEditor : Fragment(), View.OnClickListener {
    companion object {
        const val TAG = "<<_TAG_ViewSensorEditor_>>"
        const val SENSOR_ID = "SENSOR_ID"
    }

    private lateinit var vBinder: ViewSensorEditorBinding //Layout Binder
    private val vModel: ViewModelSensorEditor by viewModels()
    private var sensorId: String? = null

    private lateinit var inpTextArray: List<TextInputLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_sensor_editor,
            container, false
        )
        this.vBinder.let { binder ->
            this.inpTextArray = listOf(
                binder.inpTxtSensorName,
                binder.inpTxtSensorType,
                binder.inpTxtSensorMAC,
                binder.inpTxtSensorNum,
                binder.inpTxtSensorZone,
                binder.inpTxtSensorLocation
            )

            this.vBinder.btnCancel.setOnClickListener(this)
            this.vBinder.btnSave.setOnClickListener(this)
        }


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

        this.vModel.getVehicleUpdateResponse().observe(viewLifecycleOwner, Observer { success->
            if (success) {
                OutputManager.displayPositiveOnlyDialog(
                    requireContext(), "Sensor Updated",
                    "Sensor updated successfully"
                )
                findNavController().popBackStack()
            } else {
                OutputManager.displayPositiveOnlyDialog(
                    requireContext(), "Failed",
                    "Something went wrong while updating the sensor details." +
                            "Please try again"
                )
            }
        })

        this.vModel.lvdSensorDetail.observe(viewLifecycleOwner, Observer {
            this.vBinder.sensorDetail = it
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            when (v.id) {
                R.id.btnSave -> {
                    this.saveEdit()
                }
                R.id.btnCancel -> {
                    findNavController().popBackStack()
                }
                else -> return
            }
        }
    }

    private fun saveEdit() {
        this.vBinder.run {
            vModel.lvdSensorDetail.value?.run {
                inpTextArray.forEachIndexed { index, textInputLayout ->
                    textInputLayout.editText?.let { editText ->
                        when (index) {
                            0 -> this.sensorName = editText.text.trim().toString()
                            1 -> this.sensorType = getSensorTypeNumber(editText.text.toString())
                            2 -> this.macAddress = editText.text.trim().toString()
                            3 -> this.serialNumber = editText.text.trim().toString()
                            4 -> this.sensorZone = editText.text.trim().toString().toInt()
                            5 -> this.sensorLocation = editText.text.trim().toString().toInt()
                        }
                    }
                }
                vModel.sensorIndex?.let { sensorIndex ->
                    vModel.updateSensor(this, sensorIndex)
                }
            }
        }
    }

    private fun getSensorTypeNumber(sensorType: String): Int {
        SensorTypeSelector.valueOf(sensorType).ordinal.also { sensorIndex ->
            return (sensorIndex + 1)
        }
    }
}