package com.arkan.terbangin.presentation.auth.otp

import android.os.Bundle
import androidx.lifecycle.ViewModel

class OTPViewModel(
    extras: Bundle?,
) : ViewModel() {
    val fullName = extras?.getString(OTPActivity.EXTRA_NAME)
    val email = extras?.getString(OTPActivity.EXTRA_EMAIL)
    val phoneNumber = extras?.getString(OTPActivity.EXTRA_PHONE)
    val password = extras?.getString(OTPActivity.EXTRA_PASSWORD)
}
