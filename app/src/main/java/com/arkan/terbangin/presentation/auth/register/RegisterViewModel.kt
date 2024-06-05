package com.arkan.terbangin.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.OTPRepository
import com.arkan.terbangin.data.source.network.model.auth.otp.request_otp.RequestOTPResponse
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(
    private val repository: OTPRepository,
) : ViewModel() {
    private val _apiResponse = MutableLiveData<RequestOTPResponse?>()
    val apiResponse: LiveData<RequestOTPResponse?> get() = _apiResponse

    fun requestOTP(email: String) =
        repository.requestOTP(
            email,
        ).asLiveData(Dispatchers.IO)
}
