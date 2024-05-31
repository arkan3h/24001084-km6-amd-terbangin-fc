package com.arkan.terbangin.presentation.auth.otp

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.repository.auth.OTPRepository
import com.arkan.terbangin.data.repository.auth.RegisterRepository
import kotlinx.coroutines.Dispatchers

class OTPViewModel(
    extras: Bundle?,
    private val otpRepository: OTPRepository,
    private val registerRepository: RegisterRepository,
) : ViewModel() {
    val fullName = extras?.getString(OTPActivity.EXTRA_NAME)
    val email = extras?.getString(OTPActivity.EXTRA_EMAIL)
    val phoneNumber = extras?.getString(OTPActivity.EXTRA_PHONE)
    val password = extras?.getString(OTPActivity.EXTRA_PASSWORD)

    fun verifyOTP(
        email: String,
        otp: String,
    ) = otpRepository.verifyOTP(
        email,
        otp,
    ).asLiveData(Dispatchers.IO)

    fun doRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) = registerRepository.doRegister(
        fullName,
        email,
        phoneNumber,
        password,
    ).asLiveData(Dispatchers.IO)

    fun requestOTP(email: String) =
        otpRepository.requestOTP(
            email,
        ).asLiveData(Dispatchers.IO)
}
