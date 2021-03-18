package ie.toxodev.thermokingtask.views.front

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
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.ViewFrontBinding
import ie.toxodev.thermokingtask.supportClasses.OutputManager
import ie.toxodev.thermokingtask.supportClasses.binderModels.VehicleInfoAdapterBinderModel
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.FixedVehicleData
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.VehicleDataResponse
import java.io.FileNotFoundException
import java.io.InputStreamReader

@AndroidEntryPoint
class ViewFront : Fragment(), View.OnClickListener,
    VehicleInfoAdapterBinderModel.ICardVehicleDetailListener {
    companion object {
        const val TAG = "<<_TAG_ViewFront_>>"
    }

    private lateinit var vBinder: ViewFrontBinding //Layout Binder
    private val vModel: ViewModelFront by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.vBinder = DataBindingUtil.inflate(
            inflater,
            R.layout.view_front,
            container, false
        )
        this.vBinder.bntLoadData.setOnClickListener(this)
        this.initializeLiveData()

        this.vModel.retrieveSavedVehicles()

        return this.vBinder.root
    }

    private fun initializeLiveData() {
        this.vModel.getSaveResult().observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                this.vModel.retrieveSavedVehicles()
            } else {
                OutputManager.logError(TAG, "SAVING FAILED")
            }
        })

        this.vModel.getSavedVehicles().observe(viewLifecycleOwner, Observer { list ->
            if (list != null) {
                if (list.isNotEmpty()) {
                    this.vBinder.tvSavedVehicles.visibility = View.VISIBLE
                    this.vBinder.bntLoadData.text = getString(R.string.btn_reload_vehicle_data)
                    vModel.lvdAdapterBinderModel.value = VehicleInfoAdapterBinderModel(list, this)
                } else {
                    this.vBinder.tvSavedVehicles.visibility = View.GONE
                    this.vBinder.bntLoadData.text = getString(R.string.btn_load_vehicle_data)
                    OutputManager.logError(TAG, "SAVED VEHICLES LIST IS EMPTY")
                }
            } else {
                OutputManager.logError(TAG, "FETCHING SAVED VEHICLES FAILED")
            }
        })

        this.vModel.lvdAdapterBinderModel.observe(viewLifecycleOwner, Observer {
            this.vBinder.recyclerAdapterModel = it
        })
    }


    private fun navigateToVehicleInfo() {
        ViewFrontDirections.toVehicleInfo().run {
            findNavController().navigate(this)
        }
    }

    private fun getData() {
        try {
            requireContext().assets.open("vehicleData").run {
                Gson().fromJson(InputStreamReader(this), VehicleDataResponse::class.java)
                    .also { vehicleData ->
                        FixedVehicleData().apply {
                            this.setData(vehicleData)
                        }.also { fixedData ->
                            vModel.saveVehicleData(fixedData)
                        }
                    }
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "FILE NOT FOUND")
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.bntLoadData -> this.getData()
            }
        }
    }

    override fun onVehicleCardClicked(view: View) {
        (view.tag as String).let { vehicleId ->
            ViewFrontDirections.toVehicleInfo(vehicleId).run {
                findNavController().navigate(this)
            }
        }
    }
}