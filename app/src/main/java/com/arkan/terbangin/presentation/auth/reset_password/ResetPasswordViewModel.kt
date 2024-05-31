package com.arkan.terbangin.presentation.auth.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.ResetPasswordRepository
import com.arkan.terbangin.data.source.network.model.resetpassword.ResetPasswordResponse
import kotlinx.coroutines.Dispatchers

class ResetPasswordViewModel(
    private val repository: ResetPasswordRepository,
) : ViewModel() {
    private val _apiResponse = MutableLiveData<ResetPasswordResponse?>()

    fun doResetPassword(email: String) = repository.doResetPassword(email).asLiveData(Dispatchers.IO)
}
