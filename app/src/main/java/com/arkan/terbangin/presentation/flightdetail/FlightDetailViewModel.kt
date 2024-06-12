package com.arkan.terbangin.presentation.flightdetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams

class FlightDetailViewModel(
    extras: Bundle?,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(FlightDetailActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(FlightDetailActivity.EXTRA_FLIGHT)
    val totalPrice = extras?.getDouble(FlightDetailActivity.EXTRA_TOTAL_PRICE)
}
