package com.arkan.terbangin.presentation.auth.otp

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.OTPRepository
import kotlinx.coroutines.Dispatchers

class OTPViewModel(
    extras: Bundle?,
    private val repository: OTPRepository,
) : ViewModel() {
    val fullName = extras?.getString(OTPActivity.EXTRA_NAME)
    val email = extras?.getString(OTPActivity.EXTRA_EMAIL)
    val phoneNumber = extras?.getString(OTPActivity.EXTRA_PHONE)
    val password = extras?.getString(OTPActivity.EXTRA_PASSWORD)

    fun verifyOTP(
        email: String,
        otp: String,
    ) = repository.verifyOTP(
        email,
        otp,
    ).asLiveData(Dispatchers.IO)
}
