package com.arkan.terbangin.utils

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.showAlertDialog(it: String) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(it)
    builder.setNegativeButton("Close") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun Fragment.showAlertDialog(it: String) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setMessage(it)
    builder.setNegativeButton("Close") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}
