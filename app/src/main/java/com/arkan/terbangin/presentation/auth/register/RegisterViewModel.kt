package com.arkan.terbangin.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.OTPRepository
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(
    private val repository: OTPRepository,
) : ViewModel() {
    fun requestOTP(email: String) =
        repository.requestOTP(
            email,
        ).asLiveData(Dispatchers.IO)
}
