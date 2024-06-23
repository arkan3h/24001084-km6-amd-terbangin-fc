package com.arkan.terbangin.presentation.checkout.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.payment.PaymentRepository
import com.arkan.terbangin.data.source.pref.UserPreference
import com.arkan.terbangin.presentation.flightdetail.FlightDetailActivity
import kotlinx.coroutines.Dispatchers

class CheckoutDetailViewModel(
    extras: Bundle?,
    preference: UserPreference,
    private val repository: PaymentRepository,
) : ViewModel() {
    val totalPrice = extras?.getDouble(CheckoutDetailActivity.EXTRA_TOTAL_PRICE)
    val flight = extras?.getParcelable<Flight>(CheckoutDetailActivity.EXTRA_FLIGHT)
    val params = extras?.getParcelable<FlightSearchParams>(FlightDetailActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val userId = preference.getUserID()

    fun createPayment(totalPrice: Int) = repository.createPayment(totalPrice).asLiveData(Dispatchers.IO)
}
