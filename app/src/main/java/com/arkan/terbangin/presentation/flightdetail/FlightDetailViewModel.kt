package com.arkan.terbangin.presentation.flightdetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository

class FlightDetailViewModel(
    extras: Bundle?,
    pref: UserPreferenceRepository,
) : ViewModel() {
    val isLoggedIn = pref.getToken()
    val params = extras?.getParcelable<FlightSearchParams>(FlightDetailActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(FlightDetailActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(FlightDetailActivity.EXTRA_FLIGHT_RETURN)
    var price = 0.0

    fun getTotalPrice() {
        when (params?.ticketClass?.name) {
            "Economy" -> {
                price = flight?.priceEconomy?.toDouble()!! + (flightReturn?.priceEconomy?.toDouble() ?: 0.0)
            }
            "Business" -> {
                price = flight?.priceBussines?.toDouble()!! + (flightReturn?.priceBussines?.toDouble() ?: 0.0)
            }
            "First Class" -> {
                price = flight?.priceFirstClass?.toDouble()!! + (flightReturn?.priceFirstClass?.toDouble() ?: 0.0)
            }
        }
    }
}
