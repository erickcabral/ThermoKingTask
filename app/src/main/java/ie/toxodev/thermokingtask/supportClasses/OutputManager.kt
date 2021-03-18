package ie.toxodev.thermokingtask.supportClasses

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object OutputManager {

    private var dialog: AlertDialog? = null


    fun displayPositiveOnlyDialog(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context).setTitle(title)
            .setMessage(message)
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

}