package com.arkan.terbangin.presentation.checkout.payment

import android.os.Bundle
import androidx.lifecycle.ViewModel

class PaymentViewModel(
    extras: Bundle?,
) : ViewModel() {
    val paymentUrl = extras?.getString(PaymentActivity.PAYMENT_URL)
}
