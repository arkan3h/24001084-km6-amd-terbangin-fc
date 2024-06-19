package com.arkan.terbangin.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.utils.UnauthorizedException
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseActivity : AppCompatActivity() {
    private val baseViewModel: BaseViewModel by viewModel()

    private fun handleUnauthorized() {
        baseViewModel.clearSession()
        navigateToLogin()
    }

    fun handleError(e: Exception) {
        if (e is UnauthorizedException) {
            showAlertUnauthorized()
        } else {
            showAlertDialog(e.message.orEmpty())
        }
    }

    private fun showAlertUnauthorized() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Session expired, please login again")
        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
            handleUnauthorized()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
