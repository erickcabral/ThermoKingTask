package ie.toxodev.thermokingtask.supportClasses

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ie.toxodev.thermokingtask.R
import ie.toxodev.thermokingtask.databinding.DialogSensorInfoBinding
import ie.toxodev.thermokingtask.supportClasses.vehicleDataModel.SensorDetail

object OutputManager {

    private var dialog: AlertDialog? = null


    fun displayPositiveOnlyDialog(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context).setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                this.dialog = null
            })
            .create().also {
                dialog = it
                it.show()
            }
    }

    fun displayChoiceDialog(
        context: Context,
        title: String,
        message: String,
        onPositiveClick: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(context).setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                this.dialog = null
            })
            .setPositiveButton("Ok", onPositiveClick)
            .create().also {
                this.dialog = it
                it.show()
            }
    }

    fun toastShort(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun sensorDetailsDialog(context: Context, sensorDetail: SensorDetail) {
        val binder = DataBindingUtil.inflate<DialogSensorInfoBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_sensor_info,
            null, false
        ).also {
            it.sensorDetail = sensorDetail
        }
        val view = binder.root
        MaterialAlertDialogBuilder(context)
            .setView(view)
            .create()
            .show()
    }
}