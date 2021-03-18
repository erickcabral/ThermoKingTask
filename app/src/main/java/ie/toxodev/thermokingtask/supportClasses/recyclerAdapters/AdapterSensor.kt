package ie.toxodev.thermokingtask.supportClasses.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.CardSensorsBinding
import ie.toxodev.thermokingtask.supportClasses.binderModels.SensorsAdapterModel

class AdapterSensor(private val adapterModel: SensorsAdapterModel) :
    RecyclerView.Adapter<AdapterSensor.ViewHolderSensors>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSensors {
        LayoutInflater.from(parent.context).run {
            DataBindingUtil.inflate<CardSensorsBinding>(this, R.layout.card_sensors, parent, false)
                .run {
                    return ViewHolderSensors(this)
                }
        }
    }

    override fun onBindViewHolder(holder: ViewHolderSensors, position: Int) {
        adapterModel.sensorList[position].run {
            this.macAddress
            holder.cardBinder.model = this
            holder.cardBinder.clickListener = adapterModel.clickListener
        }
    }

    override fun getItemCount(): Int = adapterModel.sensorList.size

    class ViewHolderSensors(val cardBinder: CardSensorsBinding) :
        RecyclerView.ViewHolder(cardBinder.root)
}