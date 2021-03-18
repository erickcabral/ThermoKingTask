package ie.toxodev.thermokingtask.supportClasses.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.CardVehicleInfoBinding
import ie.toxodev.thermokingtask.supportClasses.binderModels.VehicleInfoAdapterBinderModel

class AdapterVehicleInfo(private val adapterModel: VehicleInfoAdapterBinderModel) :
    RecyclerView.Adapter<AdapterVehicleInfo.ViewHolderVehicleInfo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVehicleInfo {
        LayoutInflater.from(parent.context).run {
            DataBindingUtil.inflate<CardVehicleInfoBinding>(
                this,
                R.layout.card_vehicle_info,
                parent,
                false
            ).run {
                return ViewHolderVehicleInfo(this)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderVehicleInfo, position: Int) {
        val model = adapterModel.vehicleList[position]
        holder.cardBinder.model = model
        holder.cardBinder.clickListener = adapterModel.clickListener
    }

    override fun getItemCount(): Int {
        return adapterModel.vehicleList.size
    }

    class ViewHolderVehicleInfo(val cardBinder: CardVehicleInfoBinding) :
        RecyclerView.ViewHolder(cardBinder.root)
}