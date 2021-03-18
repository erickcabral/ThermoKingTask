package ie.toxodev.thermokingtask.views.vehicleInfo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.ViewVehicleInfoBinding
import ie.toxodev.thermokingtask.supportClasses.OutputManager
import ie.toxodev.thermokingtask.supportClasses.binderModels.ContTextFieldBinderModel
import ie.toxodev.thermokingtask.supportClasses.binderModels.SensorsAdapterModel
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.VehicleDetail

@AndroidEntryPoint
class ViewVehicleInfo : Fragment(), SensorsAdapterModel.ISensorAdapterClickListener {
    companion object {
        const val TAG = "<<_TAG_ViewVehicleInfo_>>"
        const val VEHICLE_ID = "VEHICLE_ID"
    }

    private lateinit var vBinder: ViewVehicleInfoBinding //Layout Binder
    private val vModel: ViewModelVehicleInfo by viewModels()
    private var SELECTED_SENSOR: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vModel.let {
            it.contCustomer =
                ContTextFieldBinderModel(requireActivity().resources.getString(R.string.txt_customer))
            it.contVehicleName =
                ContTextFieldBinderModel(requireActivity().resources.getString(R.string.txt_vehicle_name))
            it.contVehicleId =
                ContTextFieldBinderModel(requireActivity().resources.getString(R.string.txt_vehicle_id))
            it.contReefer =
                ContTextFieldBinderModel(requireActivity().resources.getString(R.string.txt_reefer))
            it.contMobile =
                ContTextFieldBinderModel(requireActivity().resources.getString(R.string.txt_mobile_num))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_vehicle_info,
            container, false
        )

        this.vBinder.contMobileNumber = vModel.contMobile
        this.vBinder.contCustomer = vModel.contCustomer
        this.vBinder.contReefer = vModel.contReefer
        this.vBinder.contVehicleId = vModel.contVehicleId
        this.vBinder.contVehicleName = vModel.contVehicleName

        this.initializeLiveData()
        arguments?.let {
            val vehicleId = it.getString(VEHICLE_ID)
            if (vehicleId != null) {
                vModel.fetchVehicleInfo(vehicleId)
            } else {
                Log.e(TAG, "VEHICLE ID IS NULL")
            }

        }

        return this.vBinder.root
    }

    private fun initializeLiveData() {
        this.vModel.getVehicleInfoResponse().observe(viewLifecycleOwner, Observer {
            if (it?.vehicleDetail != null) {
                this.updateVehicleInfo(it.vehicleDetail!!)
                this.vModel.lvdVehicleInfo.value = it
            } else {
                TODO("Not implemented yet")
            }
        })
        this.vModel.getVehicleUpdateResponse().observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                vModel.lvdVehicleInfo.value.run {
                    this?.let {
                        it.vehicleDetail?.let { vehicleDetail ->
                            vModel.fetchVehicleInfo(vehicleDetail.vehicleID)
                        }
                    }
                }
            } else {
                TODO("not implemented yet")
            }
        })

        this.vModel.lvdVehicleInfo.observe(viewLifecycleOwner, Observer {
            this.vBinder.adapterModel = SensorsAdapterModel(it.sensors, this)
        })
    }

    private fun updateVehicleInfo(vehicleDetail: VehicleDetail) {
        vehicleDetail.also {
            vModel.contCustomer.apply { this.info = it.customer }.also {
                this.vBinder.contCustomer = it
            }
            vModel.contMobile.apply { this.info = it.mobileNumber }.also {
                this.vBinder.contMobileNumber = it
            }
            vModel.contReefer.apply { this.info = it.reeferSerial }.also {
                this.vBinder.contReefer = it
            }
            vModel.contVehicleName.apply { this.info = it.name }.also {
                this.vBinder.contVehicleName = it
            }
            vModel.contVehicleId.apply { this.info = it.vehicleID }.also {
                this.vBinder.contVehicleId = it
            }
        }
    }

    override fun onCardClicked(view: View) {
        (view.tag as? SensorDetail)?.let {
            OutputManager.sensorDetailsDialog(requireContext(), it)
        }
    }

    override fun onDeleteIconClicked(view: View) {
        SELECTED_SENSOR = view.tag.toString()
        OutputManager.displayChoiceDialog(
            requireContext(),
            "Delete Sensor",
            "Would you like to delete this sensor?",
            onPositiveDelete
        )
    }

    override fun onEditIconClicked(view: View) {
        view.tag.toString().run {
            val vehicleId: String = vModel.lvdVehicleInfo.value?.id.toString()
            ViewVehicleInfoDirections.toSensorDetails(vehicleId, this).run {
                findNavController().navigate(this)
            }
        }
    }

    private val onPositiveDelete: DialogInterface.OnClickListener =
        DialogInterface.OnClickListener { dialog, which ->
            OutputManager.toastShort(requireContext(), "Deleting Sensor")
            SELECTED_SENSOR?.let {
                vModel.deleteSensor(it)
            }
        }

}