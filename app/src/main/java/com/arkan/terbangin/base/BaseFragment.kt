package com.arkan.terbangin.base

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.arkan.terbangin.utils.ApiErrorException
import com.arkan.terbangin.utils.NoInternetException
import com.arkan.terbangin.utils.UnauthorizedException
import com.arkan.terbangin.utils.navigateToLogin
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment() : Fragment() {
    private val baseViewModel: BaseViewModel by viewModel()

    private fun handleUnauthorized() {
        baseViewModel.clearSession()
        navigateToLogin()
    }

    fun handleError(e: Exception) {
        when (e) {
            is UnauthorizedException -> {
                showAlertUnauthorized()
            }
            is NoInternetException -> {
                showAlertDialog("No Internet Connection")
            }
            is ApiErrorException -> {
                showAlertDialog(e.errorResponse.message)
            }
            else -> {
                showAlertDialog(e.message.orEmpty())
            }
        }
    }

    private fun showAlertUnauthorized() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Session expired, please login again")
        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
            handleUnauthorized()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
